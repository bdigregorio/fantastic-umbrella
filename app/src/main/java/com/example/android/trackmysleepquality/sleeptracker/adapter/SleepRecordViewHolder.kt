package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.databinding.ItemSleepRecordBinding
import com.example.android.trackmysleepquality.databinding.ItemSleepRecordGridBinding


/**
 * ViewHolder class for SleepRecord
 */
class SleepRecordViewHolder private constructor(
    val binding: ItemSleepRecordGridBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): SleepRecordViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSleepRecordGridBinding.inflate(inflater, parent, false)
            return SleepRecordViewHolder(binding)
        }
    }

    fun bind(record: SleepRecord) = with(binding) {
        sleepRecord = record
        executePendingBindings()
    }
}
