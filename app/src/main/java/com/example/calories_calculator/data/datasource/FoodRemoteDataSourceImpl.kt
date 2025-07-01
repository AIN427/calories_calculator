package com.example.calories_calculator.data.datasource

import com.example.calories_calculator.data.remote.api.CaloriesApiService
import com.example.calories_calculator.domain.datasource.FoodRemoteDataSource
import com.example.calories_calculator.domain.model.Food
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRemoteDataSourceImpl @Inject constructor(
    private val apiService: CaloriesApiService
) : FoodRemoteDataSource {

    override suspend fun getFoodList(): List<Food> {
        // Пока мокаем, потом заменим на реальный API
        delay(1000) // Имитация сетевого запроса

        return listOf(
            Food(1, "Apple", 52, 0.3, 14.0, 0.2, null, false),
            Food(2, "Banana", 89, 1.1, 23.0, 0.3, null, false),
            Food(3, "Chicken Breast", 165, 31.0, 0.0, 3.6, null, false)
            // В будущем: apiService.getFoods().map { it.toDomain() }
        )
    }

    override suspend fun searchFoods(query: String): List<Food> {
        // Пока простой поиск по мокам
        return getFoodList().filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    override suspend fun getFoodById(id: Long): Food? {
        return getFoodList().find { it.id == id }
    }
}