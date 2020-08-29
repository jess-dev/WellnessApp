package com.example.wellnessapp

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class JournalRepository(private val journalEntryDAO: JournalEntryDAO) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allEntries: LiveData<List<JournalEntry>> = journalEntryDAO.getJournalEntries()

    suspend fun insert(entry: JournalEntry) {
        journalEntryDAO.insert(entry)
    }
}