package com.example.novnavex.adapter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.novnavex.data.Achievement
import com.bumptech.glide.Glide
import com.example.novnavex.R
import com.example.novnavex.databinding.ItemAchievementBinding


class AchievementListAdapter(
    private val achievements: List<Achievement>,
    private val onItemClick: (Achievement) -> Unit
) : RecyclerView.Adapter<AchievementListAdapter.AchievementViewHolder>() {

    inner class AchievementViewHolder(private val binding: ItemAchievementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(achievement: Achievement) {
            binding.achievement = achievement
            binding.root.setOnClickListener { onItemClick(achievement) }

            // Use Glide to load the image
            Glide.with(binding.achievementImage.context)
                .load(achievement.imageResId) // Ensure this is a valid drawable or URL
                .error(R.drawable.error) // Optional error image
                .into(binding.achievementImage)

            // Set the color of the achievement's status
            when (achievement.status) {
                "Done" -> {
                    binding.achievementStatus.setTextColor(Color.GREEN) // Set color to green for Done
                }
                "Pending" -> {
                    binding.achievementStatus.setTextColor(Color.YELLOW) // Set color to yellow for Pending
                }
                "Not Taken" -> {
                    binding.achievementStatus.setTextColor(Color.RED) // Set color to red for Not Taken
                }
                else -> {
                    binding.achievementStatus.setTextColor(Color.GRAY) // Default color
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAchievementBinding>(
            inflater,
            R.layout.item_achievement,
            parent,
            false
        )
        return AchievementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = achievements[position]
        println("Binding item at position $position: $achievement")
        holder.bind(achievement)


    }

    override fun getItemCount(): Int = achievements.size
}

