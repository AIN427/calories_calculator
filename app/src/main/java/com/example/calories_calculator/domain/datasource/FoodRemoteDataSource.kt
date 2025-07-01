package com.example.calories_calculator.domain.datasource

import com.example.calories_calculator.domain.model.Food

/**
 * Абстракция для получения данных о еде из удаленного источника
 */
interface FoodRemoteDataSource {
    suspend fun getFoodList(): List<Food>
    suspend fun searchFoods(query: String): List<Food>
    suspend fun getFoodById(id: Long): Food?
}