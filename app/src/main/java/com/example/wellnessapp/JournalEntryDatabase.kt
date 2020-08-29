package com.example.wellnessapp

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [JournalEntry::class], version = 1, exportSchema = false)
public abstract class JournalEntryDatabase : RoomDatabase() {
    abstract fun journalDao() : JournalEntryDAO

    private class JournalEntryDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var journalEntryDao = database.journalDao()
                    // Delete all content here.
                    journalEntryDao.deleteAll()

                    // Add sample words.
                    var entry = JournalEntry(entryType = "Work", entry = "Did some things")
                    journalEntryDao.insert(entry)
                    entry = JournalEntry(entryType = "Personal", entry = "Did some things at home")
                    journalEntryDao.insert(entry)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: JournalEntryDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): JournalEntryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JournalEntryDatabase::class.java,
                    "journal_entry_database"
                ).addCallback(JournalEntryDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}