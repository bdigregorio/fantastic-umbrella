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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 */
class SleepTrackerFragment : Fragment() {
    private lateinit var binding: FragmentSleepTrackerBinding
    private val viewModel: SleepTrackerViewModel by viewModels {
        val application = requireNotNull(activity?.application)
        SleepTrackerViewModelFactory(SleepDatabase.getInstance(application).sleepRecordDao, application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSleepTrackerBinding.inflate(inflater).also { configureBinding(it) }.root

    private fun configureBinding(inflatedBinding: FragmentSleepTrackerBinding) {
        binding = inflatedBinding
        binding.lifecycleOwner = this
        binding.sleepTrackerViewModel = viewModel
    }
}
