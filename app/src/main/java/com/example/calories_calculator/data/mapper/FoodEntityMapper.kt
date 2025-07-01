package com.example.calories_calculator.data.mapper

import com.example.calories_calculator.data.local.entity.FoodEntity
import com.example.calories_calculator.domain.model.Food

/**
 * Маппер между Entity (Data слой) и Domain моделями
 */

fun FoodEntity.toDomainModel(): Food {
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

fun Food.toEntity(): FoodEntity {
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

// Для списков
fun List<FoodEntity>.toDomainModels(): List<Food> = map { it.toDomainModel() }
fun List<Food>.toEntities(): List<FoodEntity> = map { it.toEntity() }