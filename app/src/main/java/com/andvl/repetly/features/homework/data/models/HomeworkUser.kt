package com.andvl.repetly.features.homework.data.models

import com.andvl.repetly.common.data.models.Id

data class HomeworkUser(
    val id : Id,
    val name : String,
    val surname : String,
    val photoSrc : String?,
)
