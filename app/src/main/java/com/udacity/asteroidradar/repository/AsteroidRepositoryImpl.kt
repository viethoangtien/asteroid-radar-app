package com.udacity.asteroidradar.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.data.api.AsteroidApi
import com.udacity.asteroidradar.data.api.AsteroidApiService
import com.udacity.asteroidradar.data.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.api.toPicture
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.toAsteroid
import com.udacity.asteroidradar.data.database.toAsteroidEntity
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.Picture
import com.udacity.asteroidradar.utils.CalendarUtils
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AsteroidRepositoryImpl(
    private val context: Context
) : AsteroidRepository {

    private val asteroidDatabase: AsteroidDatabase by lazy {
        AsteroidDatabase.getDatabase(context)
    }

    private val asteroidApiService: AsteroidApiService by lazy {
        AsteroidApi.retrofitService
    }

    override fun getLatestAsteroidList(): LiveData<List<Asteroid>> =
        asteroidDatabase.asteroidDao.getAsteroids().map { asteroidEntities ->
            asteroidEntities.map { it.toAsteroid() }
        }

    override suspend fun deleteAndInsertAsteroids(asteroidList: List<Asteroid>) {
        asteroidDatabase.asteroidDao.deleteAllAsteroid()
        asteroidDatabase.asteroidDao.insertAll(asteroidEntities = asteroidList.map {
            it.toAsteroidEntity()
        })
    }

    override suspend fun fetchAllAsteroid(): List<Asteroid> {
        return suspendCoroutine { continuation ->
            var jsonResult: String?
            asteroidApiService.getAsteroids(
                startDate = CalendarUtils.getCurrentDateTime(),
                endDate = CalendarUtils.getNextSevenDaysFromCurrentDateTime(),
                apiKey = BuildConfig.API_KEY
            ).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    jsonResult = response.body()
                    jsonResult?.let {
                        val asteroidList = parseAsteroidsJsonResult(jsonResult = JSONObject(it))
                        Log.d(
                            "AsteroidRepositoryImpl", "fetchAllAsteroid size: ${asteroidList.size}"
                        )
                        continuation.resume(asteroidList)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    override suspend fun getImageOfTheDay(): Picture {
        val pictureResponse = asteroidApiService.getImageOfTheDay(apiKey = BuildConfig.API_KEY)
        return pictureResponse.toPicture()
    }
}