package com.andvl.repetly.features.tutorprofile

import com.andvl.repetly.features.tutorprofile.data.FirestoreTutorProfileRepository
import com.andvl.repetly.features.tutorprofile.data.TutorProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TutorProfileModule {
    @Binds
    abstract fun bindTutorProfileRepository(
        tutorProfileRepositoryImpl: FirestoreTutorProfileRepository
    ): TutorProfileRepository
}
