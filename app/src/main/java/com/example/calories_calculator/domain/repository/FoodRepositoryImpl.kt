package com.example.calories_calculator.data.repository

import com.example.calories_calculator.data.local.dao.FoodDao
import com.example.calories_calculator.data.local.entity.FoodEntity
import com.example.calories_calculator.data.remote.api.CaloriesApiService
import com.example.calories_calculator.domain.model.Food
import com.example.calories_calculator.domain.repository.FoodRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao,
    private val apiService: CaloriesApiService
) : FoodRepository {

    override fun getAllFoods(): Flow<List<Food>> {
        // Сначала пробуем получить данные из локальной БД
        return foodDao.getAllFoods().map { entities ->
            if (entities.isEmpty()) {
                // Если БД пустая, возвращаем моковые данные
                getMockFoods()
            } else {
                entities.map { it.toDomainModel() }
            }
        }
    }

    // Имитация загрузки данных с сервера/первичного заполнения
    suspend fun loadInitialData(): List<Food> {
        // Имитируем сетевой запрос
        delay(1500)

        val mockFoods = getMockFoods()

        // Сохраняем в БД для кеширования
        foodDao.insertFoods(mockFoods.map { it.toEntity() })

        return mockFoods
    }

    private fun getMockFoods(): List<Food> {
        return listOf(
            Food(1, "Apple", 52, 0.3, 14.0, 0.2, null, false),
            Food(2, "Banana", 89, 1.1, 23.0, 0.3, null, false),
            Food(3, "Chicken Breast", 165, 31.0, 0.0, 3.6, null, false),
            Food(4, "Brown Rice", 111, 2.6, 23.0, 0.9, null, false),
            Food(5, "Broccoli", 34, 2.8, 7.0, 0.4, null, false),
            Food(6, "Salmon", 208, 25.4, 0.0, 12.4, null, false),
            Food(7, "Greek Yogurt", 59, 10.0, 3.6, 0.4, null, false),
            Food(8, "Almonds", 579, 21.0, 22.0, 50.0, null, false),
            Food(9, "Sweet Potato", 86, 1.6, 20.0, 0.1, null, false),
            Food(10, "Oatmeal", 68, 2.4, 12.0, 1.4, null, false)
        )
    }

    override suspend fun getFoodById(id: Long): Food? {
        return foodDao.getFoodById(id)?.toDomainModel()
    }

    override suspend fun searchFoods(query: String): List<Food> {
        return foodDao.searchFoods(query).map { it.toDomainModel() }
    }

    override suspend fun insertFood(food: Food): Long {
        return foodDao.insertFood(food.toEntity())
    }

    override suspend fun updateFood(food: Food) {
        foodDao.updateFood(food.toEntity())
    }

    override suspend fun deleteFood(food: Food) {
        foodDao.deleteFood(food.toEntity())
    }
}

// Маппинг между Entity и Domain моделями
private fun FoodEntity.toDomainModel(): Food {
    return Food(
        id = id,
        name = name,
        caloriesPer100g = caloriesPer100g,
        proteinPer100g = proteinPer100g,
        carbsPer100g = carbsPer100g,
        fatPer100g = fatPer100g,
        imageUrl = imageUrl,
        isFavorite = isFavorite
    )
}

private fun Food.toEntity(): FoodEntity {
    return FoodEntity(
        id = id,
        name = name,
        caloriesPer100g = caloriesPer100g,
        proteinPer100g = proteinPer100g,
        carbsPer100g = carbsPer100g,
        fatPer100g = fatPer100g,
        imageUrl = imageUrl,
        isFavorite = isFavorite
    )
}