package com.andvl.repetly.features.newlesson.data.models

import com.andvl.repetly.common.data.models.lesson.Subject
import java.time.LocalDateTime

data class NewLessonItem(
    val students : List<NewLessonUserItem>,
    val subject: Subject,
    val topic: String,
    val description: String? = null,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val homework: NewLessonHomework? = null,
    val additionalMaterials: String? = null,
)
