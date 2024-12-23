package com.andvl.repetly.features.currentlesson

import com.andvl.repetly.features.currentlesson.data.CurrentLessonRepository
import com.andvl.repetly.features.currentlesson.data.CurrentLessonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrentLessonModule {
    @Binds
    abstract fun bindCurrentLessonRepository(
        currentLessonRepositoryImpl: CurrentLessonRepositoryImpl
    ): CurrentLessonRepository
}
