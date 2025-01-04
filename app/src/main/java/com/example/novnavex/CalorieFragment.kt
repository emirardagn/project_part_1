package com.example.novnavex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.novnavex.databinding.FragmentCalorieBinding
import com.example.novnavex.viewmodel.UserViewModel
import  com.example.novnavex.adapter.CalorieListAdapter

class  CalorieFragment : Fragment() {
    private lateinit var binding: FragmentCalorieBinding
    private val viewModel: UserViewModel by activityViewModels()  // Use shared ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_calorie,  // Your layout file
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe achievements data from ViewModel
        viewModel.calories.observe(viewLifecycleOwner) { calories ->
            val adapter = CalorieListAdapter(calories) { calorie ->
                val action = calorie
            }

            // Set up RecyclerView with the updated achievements list
            binding.calorieRecyclerView.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        // Observe the total calories LiveData from ViewModel
        viewModel.totalCalories.observe(viewLifecycleOwner) { totalCalories ->
            // Update the TextView that shows the total calories
            binding.totalCaloriesTextView.text = "Total Calories: $totalCalories"
        }
        // Set up the "Add Food" button click listener
        binding.addFoodButton.setOnClickListener {
            // Navigate to AddFoodFragment
            it.findNavController().navigate(R.id.action_calorieFragment_to_addFoodFragment)
        }

        // Set up the "Reset Foods" button click listener
        binding.resetFoodButton.setOnClickListener {
            viewModel.resetFoods()  // Reset the list of foods
        }

        // Set up the "Delete Food" button click listener
        binding.deleteFoodButton.setOnClickListener {
            // In this case, you would need some way to select which food to delete
            // For now, we can assume a specific food item is selected
            val foodToDelete = viewModel.calories.value?.get(0)  // Example: Delete the first food item
            foodToDelete?.let {
                viewModel.deleteFood(it)  // Delete the food
            }
        }
    }


}