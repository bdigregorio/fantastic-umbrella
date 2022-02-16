package com.example.android.trackmysleepquality.sleeptracker.adapter

import com.example.android.trackmysleepquality.database.SleepRecord

sealed class SleepAdapterItem {
    abstract val id: Long

    data class Record(val sleepRecord: SleepRecord) : SleepAdapterItem() {
        override val id = sleepRecord.id
    }

    object Header : SleepAdapterItem() {
        override val id = Long.MIN_VALUE
    }
}