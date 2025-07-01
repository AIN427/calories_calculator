package com.example.calories_calculator.domain.model

data class Food(
    val id: Long = 0,
    val name: String,
    val caloriesPer100g: Int,
    val proteinPer100g: Double,
    val carbsPer100g: Double,
    val fatPer100g: Double,
    val imageUrl: String? = null,
    val isFavorite: Boolean = false
)