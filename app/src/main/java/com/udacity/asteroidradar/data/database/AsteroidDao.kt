package com.udacity.asteroidradar.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Query("DELETE FROM asteroid")
    suspend fun deleteAllAsteroid()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroidEntities: List<AsteroidEntity>)

    @Query("SELECT * FROM asteroid ORDER BY close_approach_date")
    fun getSavedAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid WHERE close_approach_date = :currentDate ORDER BY close_approach_date")
    fun getTodayAsteroids(currentDate: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid WHERE close_approach_date != :currentDate ORDER BY close_approach_date")
    fun getNextWeekAsteroids(currentDate: String): LiveData<List<AsteroidEntity>>
}