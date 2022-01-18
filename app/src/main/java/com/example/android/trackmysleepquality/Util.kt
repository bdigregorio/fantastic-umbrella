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
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.android.trackmysleepquality.database.SleepRecord
import java.text.SimpleDateFormat

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
