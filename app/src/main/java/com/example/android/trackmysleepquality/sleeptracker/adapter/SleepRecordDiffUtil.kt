package com.example.android.trackmysleepquality.sleeptracker.adapter

import androidx.recyclerview.widget.DiffUtil

class SleepRecordDiffUtil : DiffUtil.ItemCallback<SleepAdapterItem>() {

    override fun areItemsTheSame(oldItem: SleepAdapterItem, newItem: SleepAdapterItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SleepAdapterItem, newItem: SleepAdapterItem): Boolean {
        return oldItem == newItem
    }
}