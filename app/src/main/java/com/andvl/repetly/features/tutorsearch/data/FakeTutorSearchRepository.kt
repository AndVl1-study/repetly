package com.andvl.repetly.features.tutorsearch.data

import com.andvl.repetly.common.data.models.user.Tutor
import com.andvl.repetly.common.data.sources.FakeTutorsSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeTutorSearchRepository @Inject constructor() : TutorSearchRepository {
    override suspend fun getTutors(): List<Tutor> = withContext(Dispatchers.IO) {
        FakeTutorsSource.getTutorsList()
    }
}
