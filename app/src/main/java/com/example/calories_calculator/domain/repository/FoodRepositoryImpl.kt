package com.example.calories_calculator.data.repository

import com.example.calories_calculator.data.local.dao.FoodDao
import com.example.calories_calculator.data.local.entity.FoodEntity
import com.example.calories_calculator.data.remote.api.CaloriesApiService
import com.example.calories_calculator.domain.model.Food
import com.example.calories_calculator.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao,
    private val apiService: CaloriesApiService
) : FoodRepository {

    override fun getAllFoods(): Flow<List<Food>> {
        return foodDao.getAllFoods().map { entities ->
            entities.map { it.toDomainModel() }
        }
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