package com.andvl.repetly.features.tutorsearch.data

import com.andvl.repetly.common.data.models.user.Tutor

interface TutorSearchRepository {
    suspend fun getTutors(): List<Tutor>
}
