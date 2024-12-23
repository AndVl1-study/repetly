package com.andvl.repetly.features.tutorsearch.data

import com.andvl.repetly.common.data.models.user.Tutor
import com.andvl.repetly.common.firestore.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreTutorSearchRepository @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : TutorSearchRepository {
    override suspend fun getTutors(): List<Tutor> = withContext(Dispatchers.IO) {
        firestoreRepository.getTutors()
    }
}
