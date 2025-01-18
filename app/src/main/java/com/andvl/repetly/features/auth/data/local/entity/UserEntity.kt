package com.andvl.repetly.features.auth.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val canBeTutor: Boolean,
    val lastUpdated: Long = System.currentTimeMillis()
) 