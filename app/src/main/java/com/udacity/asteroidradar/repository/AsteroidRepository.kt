package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.Picture

interface AsteroidRepository {
    suspend fun fetchAllAsteroid() : List<Asteroid>
    fun getLatestAsteroidList(): LiveData<List<Asteroid>>
    suspend fun deleteAndInsertAsteroids(asteroidList: List<Asteroid>)
    suspend fun getImageOfTheDay(): Picture
}