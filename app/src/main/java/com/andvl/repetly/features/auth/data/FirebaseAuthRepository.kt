package com.andvl.repetly.features.auth.data

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.andvl.repetly.MainActivity
import com.andvl.repetly.R
import com.andvl.repetly.features.auth.data.local.dao.UserDao
import com.andvl.repetly.features.auth.data.local.mapper.toEntity
import com.andvl.repetly.features.auth.data.model.UserData
import com.andvl.repetly.features.auth.presentation.viewmodel.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val userDao: UserDao,
    private val context: MainActivity
) : AuthRepository {
    private val TAG = AuthRepository::class.java.simpleName

    var verificationOtp: String = ""
    var resentToken: PhoneAuthProvider.ForceResendingToken? = null
    override var authState: MutableStateFlow<AuthState> =
        MutableStateFlow(AuthState.NotInitialized)
        private set

    private val authListeners = mutableListOf<(FirebaseAuth) -> Unit>()

    init {
        // Инициализация слушателя изменений состояния аутентификации
        auth.addAuthStateListener { firebaseAuth ->
            notifyAuthStateListeners(firebaseAuth)
        }
    }

    override fun addAuthStateListener(listener: (FirebaseAuth) -> Unit) {
        authListeners.add(listener)
    }

    private fun notifyAuthStateListeners(firebaseAuth: FirebaseAuth) {
        authListeners.forEach { listener ->
            listener(firebaseAuth)
        }
    }

    private val authCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.i(
                TAG,
                "onVerificationCompleted: Verification completed. ${context.getString(R.string.verification_complete)}"
            )
            authState.value =
                AuthState.Loading(message = context.getString(R.string.verification_complete))

            // Use obtained credential to sign in user
            signInWithAuthCredential(credential)
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            when (exception) {
                is FirebaseAuthInvalidCredentialsException -> {
                    authState.value = AuthState.Error(
                        exception = Exception(context.getString(R.string.verification_failed_try_again))
                    )
                }

                is FirebaseTooManyRequestsException -> {
                    authState.value = AuthState.Error(
                        exception = Exception(context.getString(R.string.quota_exceeded))
                    )
                }

                else -> {
                    authState.value = AuthState.Error(exception)
                }
            }
        }

        override fun onCodeSent(
            code: String, token:
            PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(code, token)
            verificationOtp = code
            resentToken = token
            authState.value = AuthState.Loading(
                message = context.getString(R.string.code_sent)
            )
        }

    }

    private val authBuilder: PhoneAuthOptions.Builder = PhoneAuthOptions.newBuilder(auth)
        .setCallbacks(authCallbacks)
        .setActivity(context)
        .setTimeout(120L, TimeUnit.SECONDS)

    private fun signInWithAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "signInWithAuthCredential:The sign in succeeded ")
                    authState.value = AuthState.Success(
                        message = context.getString(R.string.phone_auth_success)
                    )
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.e(TAG, context.getString(R.string.invalid_code))
                        authState.value = AuthState.Error(
                            exception = Exception(context.getString(R.string.invalid_code))
                        )

                        return@addOnCompleteListener
                    } else {
                        authState.value = AuthState.Error(task.exception)
                        Log.e(TAG, "signInWithAuthCredential: Error ${task.exception?.message}")

                    }
                }
            }
    }

    override fun getUserId(): String {
        return auth.currentUser?.uid.orEmpty()
    }

    override fun authenticate(phone: String) {
        authState.value =
            AuthState.Loading("${context.getString(R.string.code_will_be_sent)} $phone")

        val options = authBuilder
            .setPhoneNumber(phone)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        authCallbacks.onCodeSent(verificationId, token)
    }

    override fun onVerifyOtp(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationOtp, code)
        signInWithAuthCredential(credential)
    }

    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        authCallbacks.onVerificationCompleted(credential)
    }

    override fun onVerificationFailed(exception: Exception) {
        authCallbacks.onVerificationFailed(exception as FirebaseException)
    }

    override fun getUserPhone(): String {
        return auth.currentUser?.phoneNumber.orEmpty()
    }

    override suspend fun createAnonymousAccount() {
        authState.value = AuthState.Loading(context.getString(R.string.creating_guest_mode))
        try {
            auth.signInAnonymously().await()
            authState.value = AuthState.Success(context.getString(R.string.guest_account_created))
        } catch (e: Exception) {
            authState.value = AuthState.Error(e)
        }
    }

    override suspend fun checkUserExists(): Boolean = withContext(Dispatchers.IO) {
        val currentUser = auth.currentUser

        if (currentUser?.isAnonymous == true) {
            return@withContext true
        }

        val uid = currentUser?.uid
        if (uid != null) {
            // Проверяем кэш (уже в IO потоке)
            val cachedUser = userDao.getUserById(uid)
            if (cachedUser != null) {
                return@withContext true
            }

            // Если в кэше нет, проверяем Firestore
            try {
                val document = db.collection("users").document(uid).get().await()
                if (document.exists()) {
                    // Кэшируем данные пользователя
                    document.toObject(UserData::class.java)?.let { userData ->
                        userDao.insertUser(userData.toEntity(uid))
                    }
                    return@withContext true
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error checking user existence: $e")
            }
        }
        return@withContext false
    }

    override suspend fun register(user: UserData) = withContext(Dispatchers.IO) {
        val currentUid = auth.currentUser?.uid
        val userData = user.copy(
            phoneNumber = auth.currentUser?.phoneNumber ?: "No number found"
        )
        
        if (currentUid != null) {
            try {
                // Сохраняем в Firestore
                db.collection("users").document(currentUid).set(userData).await()
                
                // Кэшируем локально
                userDao.insertUser(userData.toEntity(currentUid))
            } catch (e: Exception) {
                Log.e(TAG, "Error registering user: $e")
                throw e
            }
        }
    }

    override fun isUserAuthorized(): Boolean {
        return auth.currentUser != null
    }
}
