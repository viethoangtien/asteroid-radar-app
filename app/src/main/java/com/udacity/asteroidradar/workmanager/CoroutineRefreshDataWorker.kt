package com.udacity.asteroidradar.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.AsteroidRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoroutineRefreshDataWorker(
    context: Context, params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val asteroidRepository: AsteroidRepository by lazy {
        AsteroidRepositoryImpl(context)
    }

    override suspend fun doWork(): Result {
        runCatching {
            withContext(Dispatchers.IO) {
                asteroidRepository.fetchAllAsteroid()
            }.let { asteroidList ->
                asteroidRepository.deleteAndInsertAsteroids(asteroidList = asteroidList)
                return Result.success()
            }
        }.getOrElse {
            Log.d("MainViewModel", "Fetch All Asteroid Exception: $it")
            return Result.retry()
        }
    }
}