package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.convertNumericQualityToImageRes
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.databinding.ItemSleepRecordBinding
import com.example.android.trackmysleepquality.durationToFormattedString


/**
 * ViewHolder class for SleepRecord
 */
class SleepRecordViewHolder(
    val binding: ItemSleepRecordBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): SleepRecordViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSleepRecordBinding.inflate(inflater, parent, false)
            return SleepRecordViewHolder(binding)
        }
    }

    fun bind(record: SleepRecord) = with(binding) {
        sleepIcon.setImageResource(record.qualityScore.convertNumericQualityToImageRes())
        sleepQuality.text = record.qualityScore.convertNumericQualityToString(root.resources)
        sleepDescription.text = record.durationToFormattedString(root.resources)
    }
}
