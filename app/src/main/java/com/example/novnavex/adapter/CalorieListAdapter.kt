package com.example.novnavex.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.novnavex.R
import com.example.novnavex.data.Calorie
import com.example.novnavex.databinding.ItemCalorieBinding


class CalorieListAdapter(
    private val calories: List<Calorie>,
    private val onItemDelete: (Calorie) -> Unit
) : RecyclerView.Adapter<CalorieListAdapter.CalorieViewHolder>() {

    inner class CalorieViewHolder(private val binding: ItemCalorieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(calorie: Calorie) {
            // Format the calorie value
            val formattedCalories = "100g: ${calorie.cal} cal"
            binding.calorieCal.text = formattedCalories
            val formattedPortion = "Portion: ${calorie.portion} gram"
            binding.caloriePortion.text = formattedPortion

            // Bind the remaining properties of the calorie object (optional)
            binding.calorie = calorie

            // Handle delete button click
            binding.deleteButton.setOnClickListener {
                onItemDelete(calorie) // Trigger delete callback
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalorieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCalorieBinding>(
            inflater,
            R.layout.item_calorie,
            parent,
            false
        )
        return CalorieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalorieViewHolder, position: Int) {
        val calorie = calories[position]
        println("Binding item at position $position: $calorie")
        holder.bind(calorie)
    }

    override fun getItemCount(): Int = calories.size
}
