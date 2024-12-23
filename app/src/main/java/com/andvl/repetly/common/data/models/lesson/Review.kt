package com.andvl.repetly.common.data.models.lesson

import com.andvl.repetly.common.data.models.Id

data class Review(
    val id : Id,
    val text : String,
    val attachments : List<Attachment>?
)
