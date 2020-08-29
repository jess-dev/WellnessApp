package com.example.wellnessapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider

class NewJournalActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var entryViewModel: JournalEntryViewModel
    private val newJournalEntryRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_journal)
        val newJournalText = findViewById<EditText>(R.id.etAchievementInput)
        val newJournalType = findViewById<Spinner>(R.id.achievement_type_spinner)
        val submitButton = findViewById<Button>(R.id.btnSubmit)

        entryViewModel = ViewModelProvider(this).get(JournalEntryViewModel::class.java)

        val spinner: Spinner = findViewById(R.id.achievement_type_spinner)
        spinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.achievement_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        submitButton.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(newJournalText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                var entry = JournalEntry(newJournalText.text.toString(), newJournalType.selectedItem.toString() )
                entryViewModel.insert(entry)
                replyIntent.putExtra("ENTRY", entry.entry)
                replyIntent.putExtra("TYPE", entry.entryType)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
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

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}