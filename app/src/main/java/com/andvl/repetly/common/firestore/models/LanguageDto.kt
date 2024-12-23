package com.andvl.repetly.common.firestore.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class LanguageDto(
    @DocumentId val id: String = "",
    @PropertyName("name") val name: String = "",
)
