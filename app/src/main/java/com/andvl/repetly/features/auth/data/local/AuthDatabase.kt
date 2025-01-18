package com.andvl.repetly.features.auth.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andvl.repetly.features.auth.data.local.dao.UserDao
import com.andvl.repetly.features.auth.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AuthDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    
    companion object {
        const val DATABASE_NAME = "auth_db"
    }
} 