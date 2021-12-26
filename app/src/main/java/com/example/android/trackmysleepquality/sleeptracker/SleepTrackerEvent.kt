package com.example.android.trackmysleepquality.sleeptracker

import com.example.android.trackmysleepquality.database.SleepRecord

sealed class SleepTrackerEvent {
    object Await : SleepTrackerEvent()
    class EndTracking(val sleepRecord: SleepRecord) : SleepTrackerEvent()
    object RecordsCleared : SleepTrackerEvent()
}