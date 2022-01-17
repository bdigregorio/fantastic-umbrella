package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepRecord

class SleepRecordAdapter : RecyclerView.Adapter<SleepRecordAdapter.TextItemViewHolder>() {

    var sleepRecords = listOf<SleepRecord>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = sleepRecords.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_text, parent, false) as TextView
        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val record = sleepRecords[position]
        holder.textView.text = record.qualityScore.toString()
    }

    /**
     * ViewHolder stub for validation of adapter.
     */
    class TextItemViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    companion object {
        val TAG = SleepRecordAdapter::class.simpleName
    }
}