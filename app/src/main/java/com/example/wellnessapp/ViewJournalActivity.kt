package com.example.wellnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewJournalActivity : AppCompatActivity() {
    private lateinit var entryViewModel: JournalEntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_journal)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = JournalEntryListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        entryViewModel = ViewModelProvider(this).get(JournalEntryViewModel::class.java)
        entryViewModel.allEntries.observe(this, Observer { entries ->
            entries?.let { adapter.setEntries(it) }
        })
    }
}
