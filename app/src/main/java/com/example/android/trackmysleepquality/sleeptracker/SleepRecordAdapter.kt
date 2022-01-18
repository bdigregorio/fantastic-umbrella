package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.convertNumericQualityToImageRes
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.convertToDateString
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.databinding.ItemSleepRecordBinding

class SleepRecordAdapter : RecyclerView.Adapter<SleepRecordAdapter.SleepRecordViewHolder>() {

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

    override fun onBindViewHolder(holder: SleepRecordViewHolder, position: Int) {
        val record = sleepRecords[position]
        with(holder.binding) {
            sleepIcon.setImageResource(record.qualityScore.convertNumericQualityToImageRes())
            sleepQuality.text = record.qualityScore.convertNumericQualityToString(root.resources)
            sleepDescription.text = record.startTime.convertToDateString()
        }
    }

    /**
     * ViewHolder class for SleepRecord
     */
    class SleepRecordViewHolder(val binding: ItemSleepRecordBinding) :
        RecyclerView.ViewHolder(binding.root)
}