package com.andvl.repetly.features.newlesson

import com.andvl.repetly.features.newlesson.data.repository.NewLessonRepository
import com.andvl.repetly.features.newlesson.data.repository.NewLessonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NewLessonModule {
    @Binds
    abstract fun bindNewLessonRepository(
        newLessonRepositoryImpl: NewLessonRepositoryImpl
    ): NewLessonRepository
}
