package com.example.project_part_1

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CaloriCalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calori_calculator)

        val heightEditText = findViewById<EditText>(R.id.editHeight)
        val weightEditText = findViewById<EditText>(R.id.editWeight)
        val ageEditText = findViewById<EditText>(R.id.editAge)
        val activitySpinner = findViewById<Spinner>(R.id.spinnerActivity)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.radioGroupGender)
        val calculateButton = findViewById<Button>(R.id.buttonCalculate)

        val activityLevels = arrayOf(
            "Sedentary (1.2)", "Lightly Active (1.375)",
            "Moderately Active (1.55)", "Very Active (1.725)",
            "Extra Active (1.9)"
        )
        val activityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activityLevels)
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activitySpinner.adapter = activityAdapter

        calculateButton.setOnClickListener {
            val height = heightEditText.text.toString().toDoubleOrNull()
            val weight = weightEditText.text.toString().toDoubleOrNull()
            val age = ageEditText.text.toString().toIntOrNull()
            val activityLevel = when (activitySpinner.selectedItemPosition) {
                0 -> 1.2
                1 -> 1.375
                2 -> 1.55
                3 -> 1.725
                else -> 1.9
            }

            val selectedGender = when (genderRadioGroup.checkedRadioButtonId) {
                R.id.radioFemale -> "Female"
                R.id.radioMale -> "Male"
                else -> null
            }

            if (height != null && weight != null && age != null && selectedGender != null) {
                val bmr = if (selectedGender == "Female") {
                    (10 * weight + 6.25 * height - 5 * age - 161) * activityLevel
                } else {
                    (10 * weight + 6.25 * height - 5 * age + 5) * activityLevel
                }

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("BMR_RESULT", bmr)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please Fill All!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
