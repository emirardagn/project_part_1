package com.example.novnavex

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.novnavex.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Get reference to the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Pass ViewModel to the fragments


        // Set OnClickListener for buttons
        val mainButton = findViewById<Button>(R.id.mainButton)
        val profileButton = findViewById<Button>(R.id.calculatorButton)
        val achievementButton = findViewById<Button>(R.id.achievementButton)
        val calorieButton = findViewById<Button>(R.id.calorieButton)

        mainButton.setOnClickListener {
            // Navigate to Main Fragment
            navController.navigate(R.id.mainFragment)
        }


        profileButton.setOnClickListener {
            // Get the username from the UserViewModel
            val username = userViewModel.userName.value

            // Create a Bundle and put the username in it
            val bundle = Bundle()
            if (username != null) {
                bundle.putString("userName", username)  // Passing the "userName" argument
            } else {
                // Handle case where username might be null, maybe provide a default value
                bundle.putString("userName", "Guest")  // Or any default value you prefer
            }
            navController.navigate(R.id.CalculatorFragment,bundle)
        }

        achievementButton.setOnClickListener {
            // Navigate to Achievement Fragment

            navController.navigate(R.id.achievementFragment)
        }

        calorieButton.setOnClickListener {
            // Navigate to calorie Fragment
            navController.navigate(R.id.calorieFragment)
        }

        // Optional: Handle window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
