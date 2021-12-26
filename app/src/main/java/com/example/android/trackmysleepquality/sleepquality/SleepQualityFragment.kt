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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepQualityBinding

/**
 * Fragment that displays a list of clickable icons,
 * each representing a sleep quality rating.
 * Once the user taps an icon, the quality is set in the current sleepNight
 * and the database is updated.
 */
class SleepQualityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSleepQualityBinding.inflate(inflater).also { binding ->
        binding.lifecycleOwner = this
        binding.sleepQualityViewModel = buildViewModel()
    }.root

    private fun buildViewModel(): SleepQualityViewModel {
        arguments?.let { args ->
            val sleepRecordId = SleepQualityFragmentArgs.fromBundle(args).sleepRecordId
            val application = requireNotNull(activity?.application)
            val sleepRecordDao = SleepDatabase.getInstance(application).sleepRecordDao
            val repository = SleepQualityRepository(sleepRecordDao)
            val viewModel by viewModels<SleepQualityViewModel> {
                SleepQualityViewModelFactory(
                    sleepRecordId,
                    repository
                )
            }
            return viewModel
        }

        throw IllegalStateException("Missing sleepRecordId in Fragment arguments")
    }
}
