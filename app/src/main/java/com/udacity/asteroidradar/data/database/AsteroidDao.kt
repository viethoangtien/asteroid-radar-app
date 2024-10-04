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
    fun getAsteroids(): LiveData<List<AsteroidEntity>>
}