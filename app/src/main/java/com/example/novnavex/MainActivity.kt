package com.example.novnavex

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.novnavex.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity<ImageButton : View?> : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        // Yeni ImageButton referansları
        val logoutButton = findViewById<ImageButton>(R.id.logoutButton)
        val mainButton = findViewById<ImageButton>(R.id.mainButton)
        val calculatorButton = findViewById<ImageButton>(R.id.calculatorButton)
        val achievementButton = findViewById<ImageButton>(R.id.achievementButton)
        val calorieButton = findViewById<ImageButton>(R.id.calorieButton)

        // Logout işlemi
        if (logoutButton != null) {
            logoutButton.setOnClickListener {
                auth.signOut()
                navigateToLogin()
            }
        }

        // Navigation işlemleri için NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Navigation butonları için click listeners
        if (mainButton != null) {
            mainButton.setOnClickListener {
                navController.navigate(R.id.mainFragment)
            }
        }

        if (calculatorButton != null) {
            calculatorButton.setOnClickListener {
                navController.navigate(R.id.CalculatorFragment)
            }
        }

        if (achievementButton != null) {
            achievementButton.setOnClickListener {
                navController.navigate(R.id.achievementFragment)
            }
        }

        if (calorieButton != null) {
            calorieButton.setOnClickListener {
                navController.navigate(R.id.calorieFragment)
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}