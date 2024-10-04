package com.udacity.asteroidradar.data.api

import com.squareup.moshi.Json
import com.udacity.asteroidradar.model.Picture

data class PictureResponse(
    @Json(name = "copyright") val copyright: String,
    @Json(name = "date") val date: String,
    @Json(name = "explanation") val explanation: String,
    @Json(name = "hdurl") val hdurl: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "service_version") val serviceVersion: String,
    @Json(name = "title") val title: String,
    @Json(name = "url") val url: String
)

fun PictureResponse.toPicture() = Picture(
    url = url, explanation = explanation
)