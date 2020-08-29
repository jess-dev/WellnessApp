package com.example.wellnessapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JournalEntryDAO {

    @Query("SELECT * from journal_entries ORDER BY id ASC")
    fun getJournalEntries(): LiveData<List<JournalEntry>>

    @Query("SELECT MAX(rowid) FROM journal_entries")
    fun getLastId() : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entry: JournalEntry)

    @Query("DELETE FROM journal_entries")
    suspend fun deleteAll()
}