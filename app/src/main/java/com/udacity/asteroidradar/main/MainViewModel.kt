package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants.SAVED_ASTEROID
import com.udacity.asteroidradar.model.Picture
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.AsteroidRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application = application) {

    private val _pictureLiveData: MutableLiveData<Picture> = MutableLiveData()
    val pictureLiveData: LiveData<Picture> = _pictureLiveData

    private val _filterAsteroidLiveData: MutableLiveData<Int> = MutableLiveData(SAVED_ASTEROID)

    init {
        getPictureOfTheDay()
    }

    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    asteroidRepository.getImageOfTheDay()
                }.let { picture ->
                    _pictureLiveData.value = picture
                }
            }.getOrElse {
                Log.d("MainViewModel", "Get image of the day Exception: $it")
            }
        }
    }

    private val asteroidRepository: AsteroidRepository by lazy {
        AsteroidRepositoryImpl(application)
    }

    fun getLatestAsteroidList() = _filterAsteroidLiveData.switchMap { filterType ->
        asteroidRepository.getLatestAsteroidList(filterType = filterType)
    }

    fun fetchAllAsteroids() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    asteroidRepository.fetchAllAsteroid()
                }.let { asteroidList ->
                    asteroidRepository.deleteAndInsertAsteroids(asteroidList = asteroidList)
                }
            }.getOrElse {
                Log.d("MainViewModel", "Fetch All Asteroid Exception: $it")
            }
        }
    }

    fun filterAsteroid(filterType: Int) {
        _filterAsteroidLiveData.value = filterType
    }
}