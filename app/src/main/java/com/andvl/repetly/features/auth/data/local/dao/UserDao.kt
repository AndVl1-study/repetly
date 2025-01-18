package com.andvl.repetly.features.auth.data.local.dao

import androidx.room.*
import com.andvl.repetly.features.auth.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: String)
    
    @Query("DELETE FROM users WHERE lastUpdated < :timestamp")
    suspend fun deleteOldUsers(timestamp: Long)
} 