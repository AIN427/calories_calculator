package com.example.calories_calculator.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.calories_calculator.data.local.dao.FoodDao
import com.example.calories_calculator.data.local.dao.MealDao
import com.example.calories_calculator.data.local.entity.FoodEntity
import com.example.calories_calculator.data.local.entity.MealEntity

@Database(
    entities = [
        FoodEntity::class,
        MealEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(com.example.calories_calculator.data.local.database.TypeConverters::class)
abstract class CaloriesDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun mealDao(): MealDao
}