package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.databinding.ItemSleepRecordBinding


/**
 * ViewHolder class for SleepRecord
 */
class SleepRecordViewHolder private constructor(
    private val binding: ItemSleepRecordBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): SleepRecordViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSleepRecordBinding.inflate(inflater, parent, false)
            return SleepRecordViewHolder(binding)
        }
    }

    fun bind(record: SleepRecord, clickListener: SleepRecordClickListener) = with(binding) {
        sleepRecord = record
        sleepRecordClickListener = clickListener
        executePendingBindings()
    }
}
