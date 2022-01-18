package com.example.android.trackmysleepquality.sleeptracker

sealed class SleepTrackerViewEvent {
    object SubscribeToViewModel : SleepTrackerViewEvent()
    class NavigateToQuality(val sleepRecordId: Long) : SleepTrackerViewEvent()
    object ClearAllRecords : SleepTrackerViewEvent()
}