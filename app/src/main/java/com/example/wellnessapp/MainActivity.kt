package com.example.wellnessapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var entryViewModel: JournalEntryViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val newJournalEntryRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        val currentUser = auth.currentUser

        if (currentUser != null) {
            findViewById<TextView>(R.id.userEmail).text = currentUser.email
            currentUser.displayName
            currentUser.uid
        }

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

    fun signOut(view: View) {
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            updateUI(null)
        }
    }

    private fun updateUI(account: FirebaseUser?) {
        val intent = Intent(this, SignInActivity::class.java).apply {
        }
        startActivity(intent)
    }
}