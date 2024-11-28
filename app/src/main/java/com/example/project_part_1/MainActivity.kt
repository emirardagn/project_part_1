package com.example.project_part_1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonCalculator = findViewById<Button>(R.id.buttonCalculator)
        val buttonProfile = findViewById<Button>(R.id.buttonProfile)

        buttonCalculator.setOnClickListener {
            val intent = Intent(this, CaloriCalculatorActivity::class.java)
            startActivity(intent)
        }

    }
}
