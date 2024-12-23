package com.andvl.repetly.features.messenger

import com.andvl.repetly.features.messenger.data.FakeMessengerRepository
import com.andvl.repetly.features.messenger.data.MessengerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MessengerModule {
    @Binds
    abstract fun bindMessengerRepository(
        messengerRepositoryImpl: FakeMessengerRepository
    ): MessengerRepository
}
