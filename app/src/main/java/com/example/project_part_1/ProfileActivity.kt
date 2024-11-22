package com.example.project_part_1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var buttonLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextSurname = findViewById<EditText>(R.id.editTextSurname)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        buttonLogout = findViewById(R.id.buttonLogout)

        // Kullanıcı bilgilerini yükle
        loadUserProfile(editTextName, editTextSurname, editTextEmail)

        // Log Out butonu için işlev
        buttonLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun loadUserProfile(editTextName: EditText, editTextSurname: EditText, editTextEmail: EditText) {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    editTextName.setText(document.getString("name"))
                    editTextSurname.setText(document.getString("surname"))
                    editTextEmail.setText(document.getString("email"))
                    editTextEmail.isEnabled = false // Email düzenlenemez
                } else {
                    Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun logoutUser() {
        // Firebase oturumunu kapat
        auth.signOut()

        // Kullanıcı ID'sini yerel depodan sil
        clearUserIdLocally()

        // LoginActivity'ye yönlendir
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // ProfileActivity'yi kapat
    }

    private fun clearUserIdLocally() {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("userId") // userId bilgisini sil
        editor.apply()
    }
}