package com.example.novnavex

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.novnavex.databinding.FragmentAchievementDetailBinding
import com.example.novnavex.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AchievementDetailFragment : Fragment() {

    private var achievementId: Int? = null
    private val viewModel: UserViewModel by activityViewModels() // Shared ViewModel
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Firestore ve Auth başlat
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Layout inflate
        val binding: FragmentAchievementDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_achievement_detail,
            container,
            false
        )

        // SafeArgs ile Achievement ID al
        achievementId = arguments?.let {
            AchievementDetailFragmentArgs.fromBundle(it).achievementId
        }

        // Göster ve güncelle
        achievementId?.let { id ->
            viewModel.getAchievementById(id).observe(viewLifecycleOwner) { achievement ->
                if (achievement != null) {
                    binding.achievement = achievement

                    // Görsel yükle
                    Glide.with(requireContext())
                        .load(achievement.imageResId)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(binding.achievementDetailImage)

                    // Status rengini ayarla
                    binding.achievementStatus.setTextColor(
                        when (achievement.status) {
                            "Done" -> Color.parseColor("#4CAF50")      // Yumuşak yeşil
                            "Pending" -> Color.parseColor("#FFC107")   // Yumuşak turuncu
                            else -> Color.parseColor("#B0BEC5")        // Gri-mavi
                        }
                    )

                    // "Change Status" butonu tıklanırsa
                    binding.btnChangeStatus.setOnClickListener {
                        val newStatus = getNextStatus(achievement.status)
                        updateAchievementInFirestore(
                            id,
                            achievement.category,
                            achievement.name,
                            achievement.description,
                            newStatus
                        )
                    }
                }
            }
        }

        return binding.root
    }

    private fun getNextStatus(currentStatus: String): String {
        return when (currentStatus) {
            "Not Taken" -> "Pending"
            "Pending" -> "Done"
            "Done" -> "Not Taken"
            else -> "Not Taken"
        }
    }

    private fun updateAchievementInFirestore(
        id: Int,
        category: String,
        goalName: String,
        goalDescription: String,
        newStatus: String
    ) {
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(requireContext(), "Please log in to update achievements", Toast.LENGTH_SHORT).show()
            return
        }

        // Kullanıcının mevcut achievement listesini çek
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // achievement_list'i al
                    val achievementList = document.get("achievement_list") as? ArrayList<Map<String, Any>> ?: arrayListOf()

                    // Mevcut achievement'i bul
                    val existingAchievementIndex = achievementList.indexOfFirst {
                        it["id"]?.toString()?.toIntOrNull() == id
                    }

                    if (existingAchievementIndex != -1) {
                        // Eğer achievement mevcutsa, sadece status güncellenir
                        val updatedAchievement = achievementList[existingAchievementIndex].toMutableMap()
                        updatedAchievement["status"] = newStatus

                        // Güncellenmiş achievement'i listeye yaz
                        achievementList[existingAchievementIndex] = updatedAchievement
                    } else {
                        // Eğer achievement yoksa, yeni olarak eklenir
                        val newAchievement = mapOf(
                            "id" to id,
                            "category" to category,
                            "goal_name" to goalName,
                            "goal_description" to goalDescription,
                            "status" to newStatus
                        )
                        achievementList.add(newAchievement)
                    }

                    // Güncellenmiş listeyi Firestore'a yaz
                    db.collection("users").document(userId)
                        .update("achievement_list", achievementList)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Achievement updated successfully!", Toast.LENGTH_SHORT).show()

                            // ViewModel'deki LiveData'yı güncelle
                            viewModel.getAchievementById(id).value?.status = newStatus

                            // AchievementFragment'a dön
                            findNavController().navigate(R.id.action_achievementDetailFragment_to_achievementFragment)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Failed to update: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to fetch achievements: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}