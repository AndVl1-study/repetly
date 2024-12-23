package com.andvl.repetly.features.tutorprofile.data

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.user.Tutor

interface TutorProfileRepository {
    suspend fun getTutorProfile(id: Id): Tutor
}
