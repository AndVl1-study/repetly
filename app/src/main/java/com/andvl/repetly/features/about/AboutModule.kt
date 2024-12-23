package com.andvl.repetly.features.about

import com.andvl.repetly.features.about.data.AboutRepository
import com.andvl.repetly.features.about.data.AboutRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AboutModule {
    @Binds
    abstract fun bindAboutRepository(
        aboutRepositoryImpl: AboutRepositoryImpl
    ): AboutRepository
}
