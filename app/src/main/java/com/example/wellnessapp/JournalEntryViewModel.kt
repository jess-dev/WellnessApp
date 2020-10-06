package com.example.wellnessapp

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class JournalEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: JournalRepository
    val allEntries: LiveData<List<JournalEntry>>
    private var db: FirebaseFirestore
    private var auth: FirebaseAuth

    init {
        val journalEntryDAO = JournalEntryDatabase.getDatabase(application, viewModelScope).journalDao()
        repository = JournalRepository(journalEntryDAO)
        auth = Firebase.auth
        db = Firebase.firestore
        allEntries = repository.allEntries
    }

//    fun getEntries() {
//        if (auth.currentUser != null) {
//            val journals = db.collection("users")
//                .whereEqualTo(auth.currentUser!!.uid, true)
//                .get()
//                .addOnSuccessListener { documents ->
//                    for (doc in documents) {
//                        Log.d(TAG, "${doc.id} => ${doc.data}")
//                    }
//                }
//        }
//    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(entry: JournalEntry) = viewModelScope.launch(Dispatchers.IO) {

        if (auth.currentUser != null) {
            val id = auth.currentUser!!.uid
            val userJournals = db.collection("users").document(id)
            val entry = hashMapOf(
                "entry" to entry.entry,
                "type" to entry.entryType,
                "date" to Calendar.getInstance().time
            )
            userJournals.update("journals", FieldValue.arrayUnion(entry))
        }
    }
}

