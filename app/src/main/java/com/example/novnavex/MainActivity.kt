package com.example.novnavex

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Get reference to the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Set OnClickListener for buttons
        val mainButton = findViewById<Button>(R.id.mainButton)
        val profileButton = findViewById<Button>(R.id.calculatorButton)
        val achievementButton = findViewById<Button>(R.id.achievementButton)

        mainButton.setOnClickListener {
            // Navigate to Main Fragment
            navController.navigate(R.id.mainFragment)
        }

        profileButton.setOnClickListener {
            // Navigate to Profile Fragment
            val bundle = Bundle()
            bundle.putString("userName", "John Doe")  // Passing the "userName" argument  dummy data
            navController.navigate(R.id.CalculatorFragment,bundle)
        }

        achievementButton.setOnClickListener {
            // Navigate to Achievement Fragment
            navController.navigate(R.id.achievementFragment)
        }

        // Optional: Handle window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
