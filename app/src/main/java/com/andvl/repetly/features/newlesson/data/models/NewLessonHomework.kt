package com.andvl.repetly.features.newlesson.data.models

import com.andvl.repetly.common.data.models.lesson.Attachment

data class NewLessonHomework(
    val text : String,
    val attachments : List<Attachment>?
)
