package com.andvl.repetly.features.settings

import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {

    suspend fun updateTheme(isDarkTheme : Boolean)

    suspend fun getTheme() : Flow<Boolean>
}
