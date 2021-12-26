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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSleepTrackerBinding.inflate(inflater).also { binding ->
        binding.lifecycleOwner = this
        binding.sleepTrackerViewModel = buildViewModel()
    }.root

    private fun buildViewModel(): SleepTrackerViewModel {
        val application = requireNotNull(activity?.application)
        val sleepRecordDao = SleepDatabase.getInstance(application).sleepRecordDao
        val sleepTrackerRepository = SleepTrackerRepository(sleepRecordDao)
        val viewModel by viewModels<SleepTrackerViewModel> {
            SleepTrackerViewModelFactory(
                sleepTrackerRepository,
                application
            )
        }
        return viewModel.also(::configureObservers)
    }

    private fun configureObservers(viewModel: SleepTrackerViewModel) {
        viewModel.viewEvents.observe(this) { event: SleepTrackerViewEvent ->
            Log.i(TAG, "Received SleepTrackerViewEvent: $event")
            when (event) {
                SleepTrackerViewEvent.Await -> {}
                SleepTrackerViewEvent.ShowClearedSnackbar -> {
                    showClearedRecordsSnackbar(view)
                }
                is SleepTrackerViewEvent.NavigateToQuality -> {
                    navigateToQuality(event.sleepRecordId)
                }
            }

            viewModel.eventHandled()
        }
    }

    private fun showClearedRecordsSnackbar(root: View?) {
        root?.let { rootView ->
            Snackbar.make(rootView, R.string.cleared_message, Snackbar.LENGTH_LONG).show()
        }
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
