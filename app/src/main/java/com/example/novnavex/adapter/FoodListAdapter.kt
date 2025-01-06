package com.example.novnavex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.novnavex.data.Calorie
import com.example.novnavex.databinding.ItemFoodBinding

// Modified adapter to handle 'Calorie' class
class FoodListAdapter(
    private val foodList: List<Calorie>, // List of Calorie objects
    private val onItemClick: (Calorie) -> Unit
) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Bind food data including name, category, and calories
        fun bind(food: Calorie, isSelected: Boolean) {
            binding.foodInfo.text = food.name
            binding.root.isSelected = isSelected

            binding.root.setOnClickListener {
                onItemClick(food)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = foodList.size
}
