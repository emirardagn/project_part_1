package com.example.novnavex

import androidx.recyclerview.widget.DiffUtil
import com.example.novnavex.data.Calorie

class MyDiffCallback : DiffUtil.ItemCallback<Calorie>() {
    override fun areItemsTheSame(oldItem: Calorie, newItem: Calorie): Boolean {
        // Compare item IDs
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Calorie, newItem: Calorie): Boolean {
        // Compare the actual content
        return oldItem == newItem
    }
}
