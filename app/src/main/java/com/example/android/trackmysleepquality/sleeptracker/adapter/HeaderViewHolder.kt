package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R

class HeaderViewHolder private constructor(
    view: View
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun from(parent: ViewGroup): HeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_header, parent, false)
            return HeaderViewHolder(view)
        }
    }
}