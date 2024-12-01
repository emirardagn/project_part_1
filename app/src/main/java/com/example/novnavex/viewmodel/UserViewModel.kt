package com.example.novnavex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.novnavex.R
import com.example.novnavex.data.Achievement

class UserViewModel:ViewModel() {
    val userName = MutableLiveData<String>()
    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> = _achievements

    init {
        // Initialize with dummy data
        _achievements.value = listOf(
            Achievement(1, "Workout", "Elastic Person", "Stretch or perform mobility exercises", "Pending", R.drawable.gym_image),
            Achievement(2, "Workout", "Forest Gump", "Perform at least 10,000 steps", "Pending", R.drawable.run),
            Achievement(3,"Rest",	"Sweet Dreams","Sleep at least 7-8 hours","Pending",R.drawable.sleep),
            Achievement(4,"Food",	"No Fast Food","Eat only homemade meal","Pending",R.drawable.food),
            Achievement(5,"Food",	"Caffeine Addiction","Drink a cup of coffee","Pending",R.drawable.cofee),
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

    // Function to get an achievement by ID
    fun getAchievementById(id: Int): LiveData<Achievement?> {
        val achievementLiveData = MutableLiveData<Achievement?>()
        val achievement = _achievements.value?.find { it.id == id }
        achievementLiveData.value = achievement
        return achievementLiveData
    }
}