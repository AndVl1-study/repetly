package com.andvl.repetly.features.homework

import com.andvl.repetly.features.homework.data.repository.HomeworkRepository
import com.andvl.repetly.features.homework.data.repository.HomeworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeworkModule {

    @Binds
    abstract fun bindHomeworkRepository(
        homeworkRepositoryImpl: HomeworkRepositoryImpl
    ): HomeworkRepository

}
