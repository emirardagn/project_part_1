package com.example.project_part_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val buttonCalculateAgain = findViewById<Button>(R.id.buttonCalculateAgain)
        val bmrResult = intent.getDoubleExtra("BMR_RESULT", 0.0)

        resultTextView.text = "Daily Calori You Must Take Is: ${bmrResult.toInt()} kcal"
        buttonCalculateAgain.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
