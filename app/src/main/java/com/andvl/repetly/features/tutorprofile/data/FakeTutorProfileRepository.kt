package com.andvl.repetly.features.tutorprofile.data

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.user.Tutor
import com.andvl.repetly.common.data.sources.FakeTutorsSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeTutorProfileRepository @Inject constructor() : TutorProfileRepository {
    override suspend fun getTutorProfile(id: Id): Tutor = withContext(Dispatchers.IO) {
        FakeTutorsSource.getTutorById(id)
            ?: throw NoSuchElementException("Tutor could not be found for id: ${id.value}")
    }
}
