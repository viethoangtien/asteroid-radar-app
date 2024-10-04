package com.udacity.asteroidradar.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.udacity.asteroidradar.model.Asteroid

@Entity(tableName = "asteroid", primaryKeys = ["id"])
data class AsteroidEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "code_name") val codename: String,
    @ColumnInfo(name = "close_approach_date") val closeApproachDate: String,
    @ColumnInfo(name = "absolute_magnitude") val absoluteMagnitude: Double,
    @ColumnInfo(name = "estimated_diameter") val estimatedDiameter: Double,
    @ColumnInfo(name = "relative_velocity") val relativeVelocity: Double,
    @ColumnInfo(name = "distance_from_earth") val distanceFromEarth: Double,
    @ColumnInfo(name = "is_potentially_hazardous") val isPotentiallyHazardous: Boolean
)

fun AsteroidEntity.toAsteroid(): Asteroid = Asteroid(
    id = id,
    codename = codename,
    closeApproachDate = closeApproachDate,
    absoluteMagnitude = absoluteMagnitude,
    estimatedDiameter = estimatedDiameter,
    relativeVelocity = relativeVelocity,
    distanceFromEarth = distanceFromEarth,
    isPotentiallyHazardous = isPotentiallyHazardous
)

fun Asteroid.toAsteroidEntity(): AsteroidEntity = AsteroidEntity(
    id = id,
    codename = codename,
    closeApproachDate = closeApproachDate,
    absoluteMagnitude = absoluteMagnitude,
    estimatedDiameter = estimatedDiameter,
    relativeVelocity = relativeVelocity,
    distanceFromEarth = distanceFromEarth,
    isPotentiallyHazardous = isPotentiallyHazardous
)