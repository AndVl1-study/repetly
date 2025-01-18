package com.andvl.repetly

import android.app.Application
import com.andvl.repetly.features.auth.data.service.CacheMaintenanceService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RepetlyApplication : Application() {
    @Inject
    lateinit var cacheMaintenanceService: CacheMaintenanceService

    override fun onCreate() {
        super.onCreate()
        cacheMaintenanceService.scheduleCacheCleanup()
    }
}