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
import com.example.novnavex.R
import com.example.novnavex.data.Calorie
import com.example.novnavex.databinding.ItemCalorieBinding


class CalorieListAdapter(
    private val calories: List<Calorie>,
    private val onItemClick: (Calorie) -> Unit
) : RecyclerView.Adapter<CalorieListAdapter.CalorieViewHolder>() {

    inner class CalorieViewHolder(private val binding: ItemCalorieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(calorie: Calorie) {
            binding.calorie = calorie
            binding.root.setOnClickListener { onItemClick(calorie) }



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

