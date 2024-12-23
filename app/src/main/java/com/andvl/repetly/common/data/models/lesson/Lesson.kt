package com.andvl.repetly.common.data.models.lesson

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.user.User
import java.time.LocalDateTime

data class Lesson(
    val id: Id = Id("0"),
    val subject: Subject,
    val topic: String,
    val description: String? = null,
    val studentIds: List<Id> = emptyList(),
    val tutor: User,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val homework: Homework? = null,
    val additionalMaterials: String? = null
)
