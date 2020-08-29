package com.example.wellnessapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntry(var entry: String = "", var entryType: String = "") {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}