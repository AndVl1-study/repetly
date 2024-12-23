package com.andvl.repetly.features.schedule

import com.andvl.repetly.features.schedule.data.ScheduleRepository
import com.andvl.repetly.features.schedule.data.ScheduleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleModule {
    @Binds
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl:ScheduleRepositoryImpl
    ): ScheduleRepository
}
