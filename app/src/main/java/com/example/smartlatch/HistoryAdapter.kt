package com.example.smartlatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var historyList = listOf<HistoryItem>()

    fun updateHistory(newList: List<HistoryItem>) {
        historyList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = historyList.size

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(android.R.id.text1)
        private val timestampText: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(item: HistoryItem) {
            titleText.text = item.title
            titleText.textSize = 16f
            titleText.setTextColor(android.graphics.Color.BLACK)
            
            timestampText.text = item.timestamp
            timestampText.textSize = 14f
            timestampText.setTextColor(android.graphics.Color.GRAY)
        }
    }
}
