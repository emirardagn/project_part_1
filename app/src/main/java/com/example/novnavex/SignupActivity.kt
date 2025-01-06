package com.example.novnavex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val firstNameField = findViewById<EditText>(R.id.signupFirstName)
        val lastNameField = findViewById<EditText>(R.id.signupLastName)
        val emailField = findViewById<EditText>(R.id.signupEmail)
        val passwordField = findViewById<EditText>(R.id.signupPassword)
        val signupButton = findViewById<Button>(R.id.signupButton)
        val loginRedirectButton = findViewById<Button>(R.id.loginRedirectButton)

        signupButton.setOnClickListener {
            val firstName = firstNameField.text.toString()
            val lastName = lastNameField.text.toString()
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val daily_calorie = 0;
            val achievement_list = arrayListOf<String>();

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        val user = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "email" to email,
                            "userId" to userId,
                            "daily_calorie" to daily_calorie,
                            "achievement_list" to achievement_list
                        )
                        // Firestore'da "users" koleksiyonuna kaydet
                        firestore.collection("users").document(userId!!).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                                // Login ekranına yönlendir
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Signup Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        loginRedirectButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}