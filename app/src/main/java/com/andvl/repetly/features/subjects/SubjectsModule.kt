package com.andvl.repetly.features.subjects

import com.andvl.repetly.features.subjects.data.SubjectsRepository
import com.andvl.repetly.features.subjects.data.SubjectsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SubjectsModule {
    @Binds
    abstract fun bindSubjectsRepository(
        subjectsRepositoryImpl: SubjectsRepositoryImpl
    ): SubjectsRepository
}
