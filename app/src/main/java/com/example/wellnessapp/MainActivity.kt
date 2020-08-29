package com.example.wellnessapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var entryViewModel: JournalEntryViewModel
    private val newJournalEntryRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        entryViewModel = ViewModelProvider(this).get(JournalEntryViewModel::class.java)
//        entryViewModel.allEntries.observe(this, Observer { entries ->
//            entries?.let { adapter. }
//        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            var entry = JournalEntry(intent.getSerializableExtra("ENTRY").toString(), intent.getSerializableExtra("TYPE").toString())
            entryViewModel.insert(entry)
        }

        if (requestCode == newJournalEntryRequestCode && resultCode == Activity.RESULT_OK) {
        } else {
            Toast.makeText(
                applicationContext,
                "Entry not Saved",
                Toast.LENGTH_LONG).show()
        }
    }

    fun newJournalEntry(view: View) {
        val intent = Intent(this, NewJournalActivity::class.java).apply {
        }
        startActivity(intent)
    }

    fun viewJournalEntries(view: View) {
        val intent = Intent(this, ViewJournalActivity::class.java).apply {
        }
        startActivity(intent)
    }
}