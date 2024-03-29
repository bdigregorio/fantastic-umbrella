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

package com.example.android.trackmysleepquality.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SleepQualityViewModel(
    private val sleepRecordId: Long,
    private val sleepQualityRepository: SleepQualityRepository
) : ViewModel() {

    private val _navEventDoneWithQuality = MutableLiveData<Boolean>()
    val navEventDoneWithQuality: LiveData<Boolean>
        get() = _navEventDoneWithQuality

    fun saveQualityScore(updatedQualityScore: Int) {
        viewModelScope.launch {
            val updatedSleepRecord = sleepQualityRepository.getSleepRecord(sleepRecordId).apply {
                qualityScore = updatedQualityScore
            }
            sleepQualityRepository.updateSleepRecord(updatedSleepRecord)
            _navEventDoneWithQuality.value = true
        }
    }

    fun navEventDoneHandled() {
        _navEventDoneWithQuality.value = false
    }
}