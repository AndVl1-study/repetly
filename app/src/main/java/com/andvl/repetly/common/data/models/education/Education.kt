package com.andvl.repetly.common.data.models.education

import com.andvl.repetly.common.data.models.Id

data class Education(
    val id: Id,
    val type: EducationType,
    val specialization: String? = null
)
