package com.example.novnavex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.novnavex.data.Calorie
import com.example.novnavex.databinding.FragmentAddFoodBinding
import com.example.novnavex.viewmodel.UserViewModel

class AddFoodFragment : Fragment() {
    private lateinit var binding: FragmentAddFoodBinding
    private val viewModel: UserViewModel by activityViewModels()  // Shared ViewModel
    private var selectedFood: Calorie? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load the food data from the CSV
        val foodList = readCalorieDataFromRaw(requireContext())

        if (foodList.isEmpty()) {
            Toast.makeText(requireContext(), "No data available from CSV", Toast.LENGTH_SHORT).show()
        } else {
            // Set up RecyclerView with the parsed food data
            val adapter = FoodListAdapter(foodList) { selectedItem ->
                selectedFood = selectedItem
                Toast.makeText(requireContext(), "Selected: ${selectedItem.name}", Toast.LENGTH_SHORT).show()
            }

            binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.foodRecyclerView.adapter = adapter
        }

        // Add button click logic
        binding.addFoodButton.setOnClickListener {
            val quantityText = binding.quantityInput.text.toString()
            val quantity = quantityText.toIntOrNull()

            if (selectedFood == null) {
                Toast.makeText(requireContext(), "Please select a food item", Toast.LENGTH_SHORT).show()
            } else if (quantity == null || quantity <= 0) {
                binding.quantityInput.error = "Enter a valid quantity"
            } else {
                Toast.makeText(
                    requireContext(),
                    "Confirmed: ${selectedFood!!.name} ($quantity grams, ${selectedFood!!.cal * quantity / selectedFood!!.portion} kcal)",
                    Toast.LENGTH_SHORT
                ).show()
                val food :Calorie = Calorie(selectedFood!!.id,
                    selectedFood!!.category, selectedFood!!.name,quantity,selectedFood!!.cal)
                viewModel.addFood(food)
                requireActivity().onBackPressed() // Navigate back
            }
        }
    }
}
