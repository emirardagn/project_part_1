package com.example.novnavex

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.novnavex.databinding.FragmentAchievementDetailBinding
import com.example.novnavex.viewmodel.UserViewModel

class AchievementDetailFragment : Fragment() {

    private var achievementId: Int? = null
    private val viewModel: UserViewModel by activityViewModels()  // Use shared ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve the achievementId argument passed using Safe Args
        achievementId = arguments?.let {
            AchievementDetailFragmentArgs.fromBundle(it).achievementId
        }

        // Inflate the layout
        val binding: FragmentAchievementDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_achievement_detail,  // Your layout file
            container,
            false
        )

        // Observe the LiveData for the specific achievement
        achievementId?.let { id ->
            viewModel.getAchievementById(id).observe(viewLifecycleOwner, Observer { achievement ->
                if (achievement != null) {
                    // Bind the data
                    binding.achievement = achievement

                    // Load the image using Glide
                    Glide.with(requireContext())
                        .load(achievement.imageResId)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.error)
                        .into(binding.achievementDetailImage)


                    // Set status color
                    binding.achievementStatus.setTextColor(
                        when (achievement.status) {
                            "Done" -> Color.GREEN
                            "Pending" -> Color.YELLOW
                            "Not Taken" -> Color.RED
                            else -> Color.GRAY
                        }
                    )

                    // Handle the button click to change status
                    binding.btnChangeStatus.setOnClickListener {
                        val newStatus = if (achievement.status == "Done") "Pending" else "Done"
                        viewModel.updateAchievementStatus(achievement.id, newStatus)
                    }
                }
            })
        }

        return binding.root
    }
}
