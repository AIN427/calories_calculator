package com.example.calories_calculator.presentation.food.mapper

import com.example.calories_calculator.domain.model.Food
import com.example.calories_calculator.presentation.food.model.FoodItem

fun Food.toFoodItem(): FoodItem {
    return FoodItem(
        id = id,
        name = name,
        calories = caloriesPer100g,
        description = "Protein: ${proteinPer100g}g, Carbs: ${carbsPer100g}g, Fat: ${fatPer100g}g",
        category = when {
            name.lowercase().contains("apple") || name.lowercase().contains("banana") -> "Fruits"
            name.lowercase().contains("chicken") || name.lowercase().contains("salmon") -> "Protein"
            name.lowercase().contains("rice") || name.lowercase().contains("oatmeal") -> "Grains"
            name.lowercase().contains("broccoli") || name.lowercase().contains("potato") -> "Vegetables"
            name.lowercase().contains("yogurt") -> "Dairy"
            name.lowercase().contains("almonds") -> "Nuts"
            else -> "Other"
        }
    )
}

fun List<Food>.toFoodItems(): List<FoodItem> {
    return map { it.toFoodItem() }
}