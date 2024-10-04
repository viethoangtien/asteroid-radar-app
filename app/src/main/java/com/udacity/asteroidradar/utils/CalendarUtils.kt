package com.udacity.asteroidradar.utils

import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object CalendarUtils {
    fun getCurrentDateTime(): String {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

    fun getNextSevenDaysFromCurrentDateTime(): String {
        val currentTime = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 7)
        }.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }
}