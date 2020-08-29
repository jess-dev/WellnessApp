package com.example.wellnessapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: JournalRepository
    val allEntries: LiveData<List<JournalEntry>>

    init {
        val journalEntryDAO = JournalEntryDatabase.getDatabase(application, viewModelScope).journalDao()
        repository = JournalRepository(journalEntryDAO)
        allEntries = repository.allEntries
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(entry: JournalEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(entry)
    }

}