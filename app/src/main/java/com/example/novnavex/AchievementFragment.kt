package com.example.novnavex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.novnavex.data.Achievement
import com.example.novnavex.databinding.FragmentAchievementBinding
import com.example.novnavex.viewmodel.UserViewModel
import  com.example.novnavex.adapter.AchievementListAdapter

class AchievementFragment : Fragment() {
    private lateinit var binding: FragmentAchievementBinding
    private val viewModel: UserViewModel by activityViewModels()  // Use shared ViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_achievement,  // Your layout file
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe achievements data from ViewModel
        viewModel.achievements.observe(viewLifecycleOwner) { achievements ->
            val adapter = AchievementListAdapter(achievements) { achievement ->
                val action = AchievementFragmentDirections
                    .actionAchievementFragmentToAchievementDetailFragment(achievement.id)
                view.findNavController().navigate(action)
            }

            // Set up RecyclerView with the updated achievements list
            binding.achievementRecyclerView.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }


}