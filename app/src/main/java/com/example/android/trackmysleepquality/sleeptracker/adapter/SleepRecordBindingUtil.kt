package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.convertNumericQualityToImageRes
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.durationToFormattedString


@BindingAdapter("sleepIconFrom")
fun ImageView.sleepIconFrom(sleepRecord: SleepRecord) {
    setImageResource(sleepRecord.qualityScore.convertNumericQualityToImageRes())
}

@BindingAdapter("sleepQualityFrom")
fun TextView.sleepQualityFrom(sleepRecord: SleepRecord) {
    text = sleepRecord.qualityScore.convertNumericQualityToString(context.resources)
}

@BindingAdapter("sleepDescriptionFrom")
fun TextView.sleepDescriptionFrom(sleepRecord: SleepRecord) {
    text = sleepRecord.durationToFormattedString(context.resources)
}