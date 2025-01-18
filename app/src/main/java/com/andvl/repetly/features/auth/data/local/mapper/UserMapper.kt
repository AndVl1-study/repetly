package com.andvl.repetly.features.auth.data.local.mapper

import com.andvl.repetly.features.auth.data.local.entity.UserEntity
import com.andvl.repetly.features.auth.data.model.UserData

fun UserData.toEntity(userId: String): UserEntity {
    return UserEntity(
        id = userId,
        name = name,
        surname = surname,
        phoneNumber = phoneNumber,
        canBeTutor = canBeTutor
    )
}

fun UserEntity.toUserData(): UserData {
    return UserData(
        name = name,
        surname = surname,
        phoneNumber = phoneNumber,
        canBeTutor = canBeTutor
    )
} 