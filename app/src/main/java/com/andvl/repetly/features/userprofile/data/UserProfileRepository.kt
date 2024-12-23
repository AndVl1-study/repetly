package com.andvl.repetly.features.userprofile.data

import com.andvl.repetly.features.userprofile.data.models.ProfileUserData


interface UserProfileRepository {

    suspend fun getSettingsOptions(): List<Options>
    suspend fun getUserOptions(): List<Options>
    suspend fun getUserData() : ProfileUserData
    suspend fun signOut()
}
