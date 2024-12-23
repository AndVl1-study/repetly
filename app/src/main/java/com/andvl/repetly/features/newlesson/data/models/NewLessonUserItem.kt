package com.andvl.repetly.features.newlesson.data.models

import com.andvl.repetly.common.data.models.Id

data class NewLessonUserItem(
    val id : Id,
    val name : String,
    val surname : String,
    val photoSrc : String?,
)
