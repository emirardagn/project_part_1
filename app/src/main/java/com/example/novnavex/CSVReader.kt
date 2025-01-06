package com.example.novnavex

import android.content.Context
import com.example.novnavex.data.Calorie
import com.opencsv.CSVReader
import java.io.InputStreamReader

fun readCalorieDataFromRaw(context: Context): List<Calorie> {
    val calorieList = mutableListOf<Calorie>()

    try {
        // Open the CSV file from the raw folder
        val inputStream = context.resources.openRawResource(R.raw.food)
        val reader = CSVReader(InputStreamReader(inputStream))

        // Skip the header row (if any)
        reader.readNext()

        // Read each row from the CSV
        var line: Array<String>?
        while (reader.readNext().also { line = it } != null) {
            // Parse each line into a Calorie object
            val id = line!![0].toInt()
            val category = line!![1]
            val name = line!![2]
            val portion = line!![3].toInt()
            val cal = line!![4].toInt()

            // Create the Calorie object and add it to the list
            calorieList.add(Calorie(id, category, name, portion, cal))
        }
        reader.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return calorieList
}
