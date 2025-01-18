package com.andvl.repetly.features.auth.di

import android.content.Context
import androidx.room.Room
import com.andvl.repetly.features.auth.data.local.AuthDatabase
import com.andvl.repetly.features.auth.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDatabaseModule {
    
    @Provides
    @Singleton
    fun provideAuthDatabase(
        @ApplicationContext context: Context
    ): AuthDatabase {
        return Room.databaseBuilder(
            context,
            AuthDatabase::class.java,
            AuthDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideUserDao(database: AuthDatabase): UserDao = database.userDao()
} 