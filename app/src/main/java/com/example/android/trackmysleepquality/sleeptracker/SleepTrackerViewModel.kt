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
import android.text.Spanned
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.launch

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
    private val sleepTrackerRepository: SleepTrackerRepository,
    application: Application
) : AndroidViewModel(application) {

    private var currentSleep = MutableLiveData<SleepRecord?>()
    private val sleepRecords: LiveData<List<SleepRecord>> = sleepTrackerRepository.getAllRecords()
    val formattedRecords: LiveData<Spanned> = Transformations.map(sleepRecords) { records ->
        formatNights(records, application.resources)
    }

    val isStartButtonVisible = Transformations.map(currentSleep) { it == null }
    val isStopButtonVisible = Transformations.map(currentSleep) { it != null }
    val isClearButtonVisible = Transformations.map(sleepRecords) { sleepRecords ->
        sleepRecords?.isNotEmpty()?.and(isStopButtonVisible.value?.not() ?: true)
    }

    private val _sleepTrackerEvent = MutableLiveData<SleepTrackerEvent>()
    val sleepTrackerEvent: LiveData<SleepTrackerEvent>
        get() = _sleepTrackerEvent

    init {
        _sleepTrackerEvent.value = SleepTrackerEvent.Await
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
                _sleepTrackerEvent.value = SleepTrackerEvent.EndTracking(sleepRecord)
            }
        }
    }

    fun onClickClear() {
        viewModelScope.launch {
            sleepTrackerRepository.clearAllRecords()
        }
    }

    fun eventHandled() {
        if (_sleepTrackerEvent.value != SleepTrackerEvent.Await) {
            _sleepTrackerEvent.value = SleepTrackerEvent.Await
        }
    }
}

