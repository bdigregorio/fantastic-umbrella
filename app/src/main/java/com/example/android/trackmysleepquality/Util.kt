/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality

import android.annotation.SuppressLint
import android.content.res.Resources
import com.example.android.trackmysleepquality.database.SleepRecord
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * These functions create a formatted string that can be set in a TextView.
 */
private val ONE_MINUTE_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

/**
 * These functions create a formatted string that can be set in a TextView.
 */

/**
 * Returns a string representing the numeric quality rating.
 */
fun Int.convertNumericQualityToString(resources: Resources) = when (this) {
    0 -> resources.getString(R.string.zero_very_bad)
    1 -> resources.getString(R.string.one_poor)
    2 -> resources.getString(R.string.two_soso)
    3 -> resources.getString(R.string.three_ok)
    4 -> resources.getString(R.string.four_pretty_good)
    5 -> resources.getString(R.string.five_excellent)
    else -> "--"
}

/**
 * Returns an image icon resource representing the numeric quality rating.
 */
fun Int.convertNumericQualityToImageRes() = when (this) {
    0 -> R.drawable.ic_sleep_0
    1 -> R.drawable.ic_sleep_1
    2 -> R.drawable.ic_sleep_2
    3 -> R.drawable.ic_sleep_3
    4 -> R.drawable.ic_sleep_4
    5 -> R.drawable.ic_sleep_5
    else -> R.drawable.ic_sleep_3
}

/**
 * Convert a duration to a formatted string for display.
 *
 * Examples:
 *
 * 6 seconds on Wednesday
 * 2 minutes on Monday
 * 40 hours on Thursday
 *
 * @param res resources used to load formatted strings
 */
fun SleepRecord.durationToFormattedString(res: Resources): String {
    val durationMilli = endTime - startTime
    val dateString = startTime.convertToDateString()
    return when {
        durationMilli < ONE_MINUTE_MILLIS -> {
            val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.seconds_length, seconds, dateString)
        }
        durationMilli < ONE_HOUR_MILLIS -> {
            val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.minutes_length, minutes, dateString)
        }
        else -> {
            val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.hours_length, hours, dateString)
        }
    }
}


/**
 * Take the Long milliseconds returned by the system and stored in Room,
 * and convert it to a nicely formatted string for display.
 *
 * EEEE - Display the long letter version of the weekday
 * MMM - Display the letter abbreviation of the nmotny
 * dd-yyyy - day in month and full year numerically
 * HH:mm - Hours and minutes in 24hr format
 */
@SuppressLint("SimpleDateFormat")
fun Long.convertToDateString(): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(this).toString()
}
