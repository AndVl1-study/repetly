package com.andvl.repetly.common.firestore.models

import com.google.firebase.firestore.PropertyName

data class LanguageWithLevelDto(
    @PropertyName("languageId") val languageId: String = "",
    @PropertyName("level") val level: String = ""
)
