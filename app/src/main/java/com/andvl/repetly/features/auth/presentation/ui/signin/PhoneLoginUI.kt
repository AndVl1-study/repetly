package com.andvl.repetly.features.auth.presentation.ui.signin

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andvl.repetly.R
import com.andvl.repetly.features.auth.presentation.viewmodel.AuthState
import com.andvl.repetly.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun PhoneLoginUI(
    navigateNext: () -> Unit,
    viewModel: AuthViewModel = viewModel(),
    restartLogin: () -> Unit = { viewModel.authUiState.value = AuthState.NotInitialized }
) {
    val context = LocalContext.current

    // Sign up state
    val uiState by viewModel.authUiState
        .collectAsState(initial = AuthState.NotInitialized)

    // SMS code 
    val code by viewModel.code.collectAsState(initial = "")

    // Phone number
    val phone by viewModel.number.collectAsState(initial = "")

    val focusManager = LocalFocusManager.current

    Surface {
        when (uiState) {
            // Nothing happening yet
            is AuthState.NotInitialized -> {
                EnterPhoneNumberUI(
                    modifier = Modifier
                        .padding(vertical = 56.dp, horizontal = 24.dp),
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.authenticatePhone(phone)
                    },
                    phone = phone,
                    onPhoneChange = viewModel::onNumberChange,
                    onDone = {
                        focusManager.clearFocus()
                        viewModel.authenticatePhone(phone)
                    },
                    onGuestModeButtonClicked = {
                        viewModel.createAnonymousAccount()
                    }
                )
            }

            // State loading
            is AuthState.Loading -> {
                val text = (uiState as AuthState.Loading).message
                if (text == context.getString(R.string.code_sent)) {

                    // If the code is sent, display the screen for code
                    EnterCodeUI(
                        code = code,
                        onCodeChange = viewModel::onCodeChange,
                        phone = phone,
                        onGo = {
                            Log.d("Code Sent", "The code is $code")
                            focusManager.clearFocus()
                            viewModel.verifyOtp(code)
                        })

                } else {
                    // If the loading state is different form the code sent state,
                    // show a progress indicator
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        text?.let {
                            Text(
                                it, modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

            }

            // If it is the error state, show the error UI
            is AuthState.Error -> {
                val throwable = (uiState as AuthState.Error).exception!!
                ErrorUi(exc = throwable, onRestart = restartLogin)
            }

            // You can navigate when the auth process is successful
            is AuthState.Success -> {
                navigateNext()
            }
        }
    }
}
