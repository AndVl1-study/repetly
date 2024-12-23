package com.andvl.repetly.features.tutorsearch

import com.andvl.repetly.features.tutorsearch.data.FirestoreTutorSearchRepository
import com.andvl.repetly.features.tutorsearch.data.TutorSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TutorSearchModule {
    @Binds
    abstract fun bindTutorSearchRepository(
        tutorSearchRepositoryImpl: FirestoreTutorSearchRepository
    ): TutorSearchRepository
}
