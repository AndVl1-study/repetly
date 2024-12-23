package com.andvl.repetly.common.data.models.contact

import com.andvl.repetly.common.data.models.Id

data class Contact(
    val id: Id,
    val value: String,
    val type: String
)
