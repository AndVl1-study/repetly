package com.andvl.repetly.features.messenger.data

import com.andvl.repetly.features.messenger.data.model.Chat

interface MessengerRepository {
    suspend fun getChats(): List<Chat>
}
