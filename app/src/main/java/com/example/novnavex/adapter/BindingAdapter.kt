package com.example.novnavex.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.novnavex.data.Calorie

@BindingAdapter("submitList")
fun bindRecyclerViewData(recyclerView: RecyclerView, data: List<Calorie>?) {
    (recyclerView.adapter as? CalorieListAdapter)?.submitList(data)
}