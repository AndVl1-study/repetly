package com.andvl.repetly.features.auth.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andvl.repetly.features.auth.data.local.dao.UserDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class CacheCleanupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val userDao: UserDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val oldTimestamp = System.currentTimeMillis() - CACHE_EXPIRATION
            userDao.deleteOldUsers(oldTimestamp)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        private const val CACHE_EXPIRATION = 7L * 24 * 60 * 60 * 1000 // 7 дней в миллисекундах
    }
} 