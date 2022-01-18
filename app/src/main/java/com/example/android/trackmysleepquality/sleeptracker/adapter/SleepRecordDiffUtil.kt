package com.example.android.trackmysleepquality.sleeptracker.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.android.trackmysleepquality.database.SleepRecord

class SleepRecordDiffUtil : DiffUtil.ItemCallback<SleepRecord>() {

    override fun areItemsTheSame(oldItem: SleepRecord, newItem: SleepRecord): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SleepRecord, newItem: SleepRecord): Boolean {
        return oldItem == newItem
    }
}