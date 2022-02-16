package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SleepRecordAdapter(
    private val sleepRecordClickListener: SleepRecordClickListener
) : ListAdapter<SleepAdapterItem, RecyclerView.ViewHolder>(SleepRecordDiffUtil()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SleepAdapterItem.Header -> VIEW_TYPE_HEADER
            is SleepAdapterItem.Record -> VIEW_TYPE_RECORD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            VIEW_TYPE_RECORD -> SleepRecordViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is SleepRecordViewHolder -> {
                val item = getItem(position) as SleepAdapterItem.Record
                viewHolder.bind(item.sleepRecord, sleepRecordClickListener)
            }
        }
    }

    fun submitListWithHeaders(list: List<SleepRecord>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(SleepAdapterItem.Header)
                else -> listOf(SleepAdapterItem.Header) + list.map { SleepAdapterItem.Record(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_RECORD = 1
    }
}