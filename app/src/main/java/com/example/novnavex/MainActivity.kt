package com.example.novnavex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.novnavex.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if user is logged in, otherwise redirect to login
        if (firebaseAuth.currentUser == null) {
            navigateToLogin()
            return
        }

        // Get reference to the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Set OnClickListener for buttons
        val mainButton = findViewById<Button>(R.id.mainButton)
        val profileButton = findViewById<Button>(R.id.calculatorButton)
        val achievementButton = findViewById<Button>(R.id.achievementButton)
        val calorieButton = findViewById<Button>(R.id.calorieButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        mainButton.setOnClickListener {
            // Navigate to Main Fragment
            navController.navigate(R.id.mainFragment)
        }

        profileButton.setOnClickListener {
            // Navigate to Calculator Fragment with username
            val username = userViewModel.userName.value ?: "Guest"
            val bundle = Bundle()
            bundle.putString("userName", username)
            navController.navigate(R.id.CalculatorFragment, bundle)
        }

        achievementButton.setOnClickListener {
            // Navigate to Achievement Fragment
            navController.navigate(R.id.achievementFragment)
        }

        calorieButton.setOnClickListener {
            // Navigate to Calorie Fragment
            navController.navigate(R.id.calorieFragment)
        }

        logoutButton.setOnClickListener {
            // Logout user and redirect to LoginActivity
            firebaseAuth.signOut()
            navigateToLogin()
        }

        // Optional: Handle window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

    }
}
