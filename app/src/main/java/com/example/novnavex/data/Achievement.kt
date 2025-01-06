package com.example.novnavex.data

data class Achievement(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    var status: String,
    val imageResId: Int
)