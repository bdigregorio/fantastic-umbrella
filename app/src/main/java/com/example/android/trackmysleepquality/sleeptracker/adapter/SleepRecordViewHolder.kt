package com.example.android.trackmysleepquality.sleeptracker.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.convertNumericQualityToImageRes
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.convertToDateString
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.databinding.ItemSleepRecordBinding


/**
 * ViewHolder class for SleepRecord
 */
class SleepRecordViewHolder(
    val binding: ItemSleepRecordBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(record: SleepRecord) = with(binding) {
        sleepIcon.setImageResource(record.qualityScore.convertNumericQualityToImageRes())
        sleepQuality.text = record.qualityScore.convertNumericQualityToString(root.resources)
        sleepDescription.text = record.startTime.convertToDateString()
    }
}
