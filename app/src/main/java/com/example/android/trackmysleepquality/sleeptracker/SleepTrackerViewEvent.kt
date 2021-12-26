package com.example.android.trackmysleepquality.sleeptracker

sealed class SleepTrackerViewEvent {
    object Await : SleepTrackerViewEvent()
    class NavigateToQuality(val sleepRecordId: Long) : SleepTrackerViewEvent()
    object ShowClearedSnackbar : SleepTrackerViewEvent()
}