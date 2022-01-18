package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepRecord

class SleepRecordAdapter : RecyclerView.Adapter<SleepRecordViewHolder>() {

    var sleepRecords = listOf<SleepRecord>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = sleepRecords.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepRecordViewHolder {
        return SleepRecordViewHolder.from(parent)
    }

    override fun onBindViewHolder(viewHolder: SleepRecordViewHolder, position: Int) {
        viewHolder.bind(sleepRecords[position])
    }

}