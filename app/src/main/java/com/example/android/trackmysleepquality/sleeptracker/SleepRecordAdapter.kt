package com.example.android.trackmysleepquality.sleeptracker

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepRecord

class SleepRecordAdapter : RecyclerView.Adapter<SleepRecordAdapter.TextItemViewHolder>() {

    val sleepRecords = mutableListOf<SleepRecord>()

    override fun getItemCount() = sleepRecords.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        (View.inflate(parent.context, R.layout.item_text, parent) as? TextView)?.let { textView ->
            TextItemViewHolder(textView)
        } ?: throw IllegalStateException("Inflated view does not fit Viewholder")

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val record = sleepRecords[position]
        holder.textView.text = record.qualityScore.toString()
    }


    /**
     * ViewHolder stub for validation of adapter.
     */
    class TextItemViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}