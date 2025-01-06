package com.example.novnavex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.novnavex.R
import com.example.novnavex.data.Achievement
import com.example.novnavex.data.Calorie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel : ViewModel() {
    val userName = MutableLiveData<String>()
    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> = _achievements
    private val _calories = MutableLiveData<List<Calorie>>()
    val calories: LiveData<List<Calorie>> = _calories

    // Mutable LiveData for total calories
    private val _totalCalories = MutableLiveData<Int>()
    val totalCalories: LiveData<Int> get() = _totalCalories

    // Firestore ve FirebaseAuth için referanslar
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val defaultAchievements = listOf(
        Achievement(1, "Workout", "Elastic Person", "Stretch or perform mobility exercises", "Not Taken", R.drawable.gym_image),
        Achievement(2, "Workout", "Forest Gump", "Perform at least 10,000 steps", "Not Taken", R.drawable.run),
        Achievement(3, "Rest", "Sweet Dreams", "Sleep at least 7-8 hours", "Not Taken", R.drawable.sleep),
        Achievement(4, "Food", "No Fast Food", "Eat only homemade meal", "Not Taken", R.drawable.food),
        Achievement(5, "Food", "Caffeine Addiction", "Drink a cup of coffee", "Not Taken", R.drawable.cofee),
        Achievement(6, "Food", "Perfect Diet", "Consume a balanced meal with all macronutrients (protein, carbs, fats)", "Not Taken", R.drawable.diet),
        Achievement(7, "Workout", "Core Task", "Perform a 1-minute plank", "Not Taken", R.drawable.plank),
        Achievement(8, "Rest", "Digital Detoxer", "Reduce screen time", "Not Taken", R.drawable.screen_time)
    )

    init {
        // Default olarak ViewModel içindeki achievements, dummy data ile başlar
        // Kullanıcı giriş yaptıysa Firestore'daki verilerle başlat
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            loadAchievementsFromFirestore(userId)
        } else {
            // Giriş yapılmamışsa dummy data kullan
            _achievements.value = defaultAchievements
        }
    }

    // Sync achievements with Firestore
    fun syncAchievementsWithFirestore() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            // Kullanıcı giriş yapmamış
            return
        }

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val firestoreAchievements = document.get("achievement_list") as? List<Map<String, Any>> ?: emptyList()

                    // Log Firestore'dan alınan veriler
                    firestoreAchievements.forEach {
                        Log.d("FirestoreAchievement", "Firestore Achievement: $it")
                    }

                    val userAchievements = firestoreAchievements.mapNotNull {
                        val id = it["id"]?.toString()?.toIntOrNull()
                        val status = it["status"]?.toString()
                        if (id != null && status != null) {
                            id to status
                        } else {
                            null
                        }
                    }

                    // Log Firestore'dan dönüştürülen veriler
                    userAchievements.forEach {
                        Log.d("UserAchievement", "User Achievement: ID=${it.first}, Status=${it.second}")
                    }

                    // Varsayılan listeyle birleştir
                    val mergedAchievements = mergeAchievements(_achievements.value ?: emptyList(), userAchievements)

                    // Log birleştirilmiş liste
                    mergedAchievements.forEach {
                        Log.d("MergedAchievement", "Merged Achievement: $it")
                    }

                    // LiveData'yı güncelle
                    _achievements.value = mergedAchievements
                }
            }
            .addOnFailureListener {
                // Firestore hatası (isteğe bağlı: kullanıcıya hata göster)
                Log.e("FirestoreError", "Failed to fetch achievements: ${it.message}")
            }
    }

    fun loadAchievementsFromFirestore(userId: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val userAchievements = document.get("achievement_list") as? ArrayList<Map<String, Any>>
                    if (userAchievements != null) {
                        // Firestore'dan alınan verileri defaultAchievements ile birleştir
                        val updatedAchievements = mergeAchievements(
                            defaultAchievements,
                            userAchievements.map { Pair(it["id"].toString().toInt(), it["status"].toString()) }
                        )
                        _achievements.value = updatedAchievements
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hata durumunda dummy datayı koruyabiliriz
                _achievements.value = defaultAchievements
            }
    }

    private fun mergeAchievements(
        defaultAchievements: List<Achievement>,
        userAchievements: List<Pair<Int, String>>
    ): List<Achievement> {
        return defaultAchievements.map { achievement ->
            val userAchievement = userAchievements.find { it.first == achievement.id }
            if (userAchievement != null) {
                achievement.copy(status = userAchievement.second)
            } else {
                achievement
            }
        }
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

    // Function to get a calorie by ID
    fun getCalorieById(id: Int): LiveData<Calorie?> {
        val calorieLiveData = MutableLiveData<Calorie?>()
        val calorie = _calories.value?.find { it.id == id }
        calorieLiveData.value = calorie
        return calorieLiveData
    }

    // Add food to the list
    fun addFood(calorie: Calorie) {
        val currentList = _calories.value?.toMutableList() ?: mutableListOf()
        currentList.add(calorie)
        _calories.value = currentList
        updateTotalCalories()
    }

    // Delete a specific food by id
    fun deleteFood(food: Calorie) {
        val currentList = _calories.value?.toMutableList() ?: mutableListOf()
        currentList.remove(food)
        _calories.value = currentList
        updateTotalCalories()
    }

    // Reset all foods (clear the list)
    fun resetFoods() {
        _calories.value = emptyList()
        updateTotalCalories()
    }

    private fun updateTotalCalories() {
        val total = _calories.value?.sumOf {
            // Calculate calories based on portion size and calories per 100 grams
            (it.cal * it.portion) / 100.0
        }?.let {
            // Round the result and convert to integer
            it.toInt()
        } ?: 0
        _totalCalories.value = total
    }
}