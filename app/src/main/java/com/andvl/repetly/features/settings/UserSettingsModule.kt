package com.andvl.repetly.features.settings

import android.content.Context
import com.andvl.repetly.features.tutorprofile.data.FakeTutorProfileRepository
import com.andvl.repetly.features.tutorprofile.data.TutorProfileRepository
import com.andvl.repetly.features.userprofile.data.ActualProfileRepository
import com.andvl.repetly.features.userprofile.data.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun provideSettings(
        @ApplicationContext context: Context
    ): UserSettingsRepository = UserSettingsRepositoryImpl(context)

}
