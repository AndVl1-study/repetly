package com.andvl.repetly.features.subjects.data

import com.andvl.repetly.common.data.models.lesson.Subject
import com.andvl.repetly.common.data.models.lesson.SubjectWithPrice
import com.andvl.repetly.common.firestore.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SubjectsRepositoryImpl @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : SubjectsRepository {
    override suspend fun getSubjects(): List<Subject> = withContext(Dispatchers.IO) {
        firestoreRepository.getSubjects()
    }

    override suspend fun getUserSubjects(): List<SubjectWithPrice>? = withContext(Dispatchers.IO) {
        firestoreRepository.getCurrentUserSubjectsWithPrices()
    }

    override suspend fun updateUserSubjects(userSubjects : List<SubjectWithPrice>?) {
        firestoreRepository.updateCurrentUserSubjectsWithPrices(userSubjects)
    }


}
