package com.example.android.trackmysleepquality.sleeptracker

sealed class SleepTrackerEvent {
    object Await : SleepTrackerEvent()
    class Stop(val sleepRecordId: Long) : SleepTrackerEvent()
    object Clear : SleepTrackerEvent()
    class View(val sleepRecordId: Long) : SleepTrackerEvent()
}