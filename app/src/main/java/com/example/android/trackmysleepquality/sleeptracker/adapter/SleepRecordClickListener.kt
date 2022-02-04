package com.example.android.trackmysleepquality.sleeptracker.adapter

import com.example.android.trackmysleepquality.database.SleepRecord

class SleepRecordClickListener(private val listener: (sleepRecordId: Long) -> Unit) {
    fun onClick(sleepRecord: SleepRecord) = listener(sleepRecord.id)
}