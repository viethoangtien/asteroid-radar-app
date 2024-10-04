package com.udacity.asteroidradar

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.udacity.asteroidradar.workmanager.CoroutineRefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        setupWork()
    }

    private fun setupWork() {
        applicationScope.launch {
            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true).setRequiresCharging(true).build()
            val repeatingRequest = PeriodicWorkRequestBuilder<CoroutineRefreshDataWorker>(
                1, TimeUnit.DAYS
            ).setConstraints(constraints).build()
            WorkManager.getInstance(this@AsteroidApplication).enqueueUniquePeriodicWork(
                Constants.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, repeatingRequest
            )
        }
    }
}