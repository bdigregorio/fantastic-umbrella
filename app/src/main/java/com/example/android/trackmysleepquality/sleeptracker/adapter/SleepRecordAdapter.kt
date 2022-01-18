package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.databinding.ItemSleepRecordBinding

class SleepRecordAdapter : RecyclerView.Adapter<SleepRecordViewHolder>() {

    var sleepRecords = listOf<SleepRecord>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = sleepRecords.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepRecordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSleepRecordBinding.inflate(inflater, parent, false)
        return SleepRecordViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SleepRecordViewHolder, position: Int) {
        viewHolder.bind(sleepRecords[position])
    }

}