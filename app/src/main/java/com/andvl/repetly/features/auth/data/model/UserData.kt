package com.andvl.repetly.features.auth.data.model

data class UserData(
    val name: String = "",
    val surname: String = "",
    val phoneNumber: String = "",
    val canBeTutor: Boolean = false
)
