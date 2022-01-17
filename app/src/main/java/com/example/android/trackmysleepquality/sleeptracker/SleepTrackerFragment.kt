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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 */
class SleepTrackerFragment : Fragment() {
    val binding by lazy { FragmentSleepTrackerBinding.inflate(layoutInflater) }
    val viewModel by viewModels<SleepTrackerViewModel> {
        val application = requireNotNull(activity?.application)
        val sleepRecordDao = SleepDatabase.getInstance(application).sleepRecordDao
        val sleepTrackerRepository = SleepTrackerRepository(sleepRecordDao)
        SleepTrackerViewModelFactory(
            sleepTrackerRepository,
            application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = this
        binding.sleepTrackerViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        subscribeToViewModelEvents(viewModel)
    }

    private fun configureRecyclerView() {
        val sleepRecordAdapter = SleepRecordAdapter()
        binding.sleepRecordRecyclerView.adapter = sleepRecordAdapter
        viewModel.sleepRecords.observe(viewLifecycleOwner) {
            Log.d(TAG, "Observed change in viewmodel's sleep records, updating adapter")
            it?.let { sleepRecordAdapter.sleepRecords = it }
        }
    }

    private fun subscribeToViewModelEvents(viewModel: SleepTrackerViewModel) {
        viewModel.viewEvents.observe(viewLifecycleOwner) { event: SleepTrackerViewEvent ->
            Log.i(TAG, "Received SleepTrackerViewEvent: $event")
            when (event) {
                SleepTrackerViewEvent.SubscribeToViewModel -> {
                    Log.d(TAG, "Observing event stream from ViewModel")
                }
                SleepTrackerViewEvent.ClearAllRecords -> {
                    showClearedRecordsSnackbar(view)
                }
                is SleepTrackerViewEvent.NavigateToQuality -> {
                    navigateToQuality(event.sleepRecordId)
                }
            }
        }
    }

    private fun showClearedRecordsSnackbar(root: View?) {
        Snackbar.make(binding.root, R.string.cleared_message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToQuality(sleepRecordId: Long) {
        findNavController().navigate(
            SleepTrackerFragmentDirections
                .actionSleepTrackerFragmentToSleepQualityFragment(sleepRecordId)
        )
    }

    companion object {
        val TAG = SleepTrackerFragment::class.simpleName
    }
}
