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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepRecord
import kotlinx.coroutines.launch

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
    private val sleepTrackerRepository: SleepTrackerRepository
) : ViewModel() {

    private var currentSleep = MutableLiveData<SleepRecord?>()
    val sleepRecords: LiveData<List<SleepRecord>> = sleepTrackerRepository.getAllRecords()

    val isStartButtonVisible = Transformations.map(currentSleep) { it == null }
    val isStopButtonVisible = Transformations.map(currentSleep) { it != null }
    val isClearButtonVisible = Transformations.map(sleepRecords) { sleepRecords ->
        sleepRecords?.isNotEmpty()?.and(isStopButtonVisible.value?.not() ?: true)
    }

    private val _sleepTrackerEvent = MutableLiveData<SleepTrackerEvent>(SleepTrackerEvent.Await)
    val sleepTrackerEvent: LiveData<SleepTrackerEvent>
        get() = _sleepTrackerEvent

    init {
        viewModelScope.launch {
            currentSleep.value = sleepTrackerRepository.getCurrentSleep()
        }
    }

    fun awaitNextEvent() {
        _sleepTrackerEvent.value = SleepTrackerEvent.Await
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
                _sleepTrackerEvent.value = SleepTrackerEvent.Stop(sleepRecord.id)
            }
        }
    }

    fun onClickClear() {
        viewModelScope.launch {
            sleepTrackerRepository.clearAllRecords()
            _sleepTrackerEvent.value = SleepTrackerEvent.Clear
        }
    }

    fun onSleepRecordClicked(sleepRecordId: Long) {
        _sleepTrackerEvent.value = SleepTrackerEvent.View(sleepRecordId)
    }
}

