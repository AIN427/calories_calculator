package com.example.calories_calculator.presentation.food.model

data class FoodItem(
    val id: Long,
    val name: String,
    val calories: Int,
    val description: String,
    val category: String
)