package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.android.trackmysleepquality.database.SleepRecord

class SleepRecordAdapter : ListAdapter<SleepRecord, SleepRecordViewHolder>(SleepRecordDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepRecordViewHolder {
        return SleepRecordViewHolder.from(parent)
    }

    override fun onBindViewHolder(viewHolder: SleepRecordViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

}