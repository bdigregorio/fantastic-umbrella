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

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepRecord
import kotlinx.coroutines.launch

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
    private val sleepTrackerRepository: SleepTrackerRepository,
    application: Application
) : AndroidViewModel(application) {
    private var currentSleep = MutableLiveData<SleepRecord?>()
    private val sleepRecords = sleepTrackerRepository.getAllRecords()

    init {
        viewModelScope.launch {
            currentSleep.value = sleepTrackerRepository.getCurrentSleep()
        }
    }

    fun onClickStart() {
        viewModelScope.launch {
            sleepTrackerRepository.addNewRecord(SleepRecord())
            currentSleep.value = sleepTrackerRepository.getCurrentSleep()
        }
    }

    fun onClickStop() {
        viewModelScope.launch {
            currentSleep.value?.let { sleepRecord ->
                sleepRecord.endTime = System.currentTimeMillis()
                sleepTrackerRepository.update(sleepRecord)
            }
        }
    }

    fun onClickClear() {
        viewModelScope.launch {
            sleepTrackerRepository.clearAllRecords()
        }
    }
}

