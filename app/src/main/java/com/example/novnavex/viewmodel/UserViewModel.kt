package com.example.novnavex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.novnavex.R
import com.example.novnavex.data.Achievement
import com.example.novnavex.data.Calorie

class UserViewModel:ViewModel() {
    val userName = MutableLiveData<String>()
    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> = _achievements
    private val _calories = MutableLiveData<List<Calorie>>()
    val calories: LiveData<List<Calorie>> = _calories

    init {
        // Initialize with dummy data
        _achievements.value = listOf(
            Achievement(1, "Workout", "Elastic Person", "Stretch or perform mobility exercises", "Pending", R.drawable.gym_image),
            Achievement(2, "Workout", "Forest Gump", "Perform at least 10,000 steps", "Pending", R.drawable.run),
            Achievement(3,"Rest",	"Sweet Dreams","Sleep at least 7-8 hours","Pending",R.drawable.sleep),
            Achievement(4,"Food",	"No Fast Food","Eat only homemade meal","Pending",R.drawable.food),
            Achievement(5,"Food",	"Caffeine Addiction","Drink a cup of coffee","Pending",R.drawable.cofee),
            Achievement(6, "Food", "Perfect Diet", "Consume a balanced meal with all macronutrients (protein, carbs, fats)", "Pending", R.drawable.diet),
            Achievement(7, "Workout", "Core Task", "Perform a 1-minute plank", "Pending", R.drawable.plank),
            Achievement(8, "Rest", "Digital Detoxer", "Reduce screen time", "Pending", R.drawable.screen_time),
            )
        _calories.value = listOf(
            Calorie(1,"CannedFruit","Canned Blueberries",100,88),
            Calorie(2,"PotatoProducts","Rosemary Potatoes",100,93)
        )

    }

    // Update status of a specific achievement
    fun updateAchievementStatus(id: Int, newStatus: String) {
        _achievements.value = _achievements.value?.map {
            if (it.id == id) {
                it.copy(status = newStatus)
            } else {
                it
            }
        }
    }

    fun addFood(id: String, quantity: Int) {

    }
    // Function to get an achievement by ID
    fun getAchievementById(id: Int): LiveData<Achievement?> {
        val achievementLiveData = MutableLiveData<Achievement?>()
        val achievement = _achievements.value?.find { it.id == id }
        achievementLiveData.value = achievement
        return achievementLiveData
    }

    // Function to get an calorie by ID
    fun getCalorieById(id: Int): LiveData<Calorie?> {
        val calorieLiveData = MutableLiveData<Calorie?>()
        val calorie = _calories.value?.find { it.id == id }
        calorieLiveData.value = calorie
        return calorieLiveData
    }

    // Add a food to the list
    fun addFood(calorie: Calorie) {
        val currentList = _calories.value?.toMutableList() ?: mutableListOf()
        currentList.add(calorie)
        _calories.value = currentList
    }
}