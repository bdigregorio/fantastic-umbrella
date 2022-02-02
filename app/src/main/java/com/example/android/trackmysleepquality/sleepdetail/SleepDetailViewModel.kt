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

package com.example.android.trackmysleepquality.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.database.SleepRecordDao

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param sleepRecordId The key of the current night we are working on.
 */
class SleepDetailViewModel(
    private val sleepRecordId: Long = 0L,
    private val dataSource: SleepRecordDao
) : ViewModel() {

    private val sleepRecord = MediatorLiveData<SleepRecord>()

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    init {
        sleepRecord.addSource(dataSource.getSleepRecordLiveData(sleepRecordId), sleepRecord::setValue)
    }

    fun getSleepRecord() = sleepRecord

    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }
}

