package com.example.wellnessapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JournalEntryListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<JournalEntryListAdapter.JournalEntryViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var entries = emptyList<JournalEntry>()

    inner class JournalEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val journalItemEntry: TextView = itemView.findViewById(R.id.tvThisEntry)
        val journalItemType: TextView = itemView.findViewById(R.id.tvThisType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalEntryViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return JournalEntryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JournalEntryViewHolder, position: Int) {
        val current = entries[position]
        holder.journalItemEntry.text = current.entry
        holder.journalItemType.text = current.entryType
    }

    internal fun setEntries(entries: List<JournalEntry>) {
        this.entries = entries
        notifyDataSetChanged()
    }

    override fun getItemCount() = entries.size
}