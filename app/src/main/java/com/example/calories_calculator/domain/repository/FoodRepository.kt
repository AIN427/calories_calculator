package com.example.calories_calculator.domain.repository

import com.example.calories_calculator.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {

    fun getAllFoods(): Flow<List<Food>>
    suspend fun getFoodById(id: Long): Food?
    suspend fun searchFoods(query: String): List<Food>
    suspend fun insertFood(food: Food): Long
    suspend fun updateFood(food: Food)
    suspend fun deleteFood(food: Food)
}