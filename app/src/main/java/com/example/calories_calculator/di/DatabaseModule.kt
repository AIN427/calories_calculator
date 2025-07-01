package com.example.calories_calculator.di

import com.example.calories_calculator.data.local.dao.FoodDao
import com.example.calories_calculator.data.local.dao.MealDao
import com.example.calories_calculator.data.local.database.CaloriesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideFoodDao(database: CaloriesDatabase): FoodDao {
        return database.foodDao()
    }

    @Provides
    fun provideMealDao(database: CaloriesDatabase): MealDao {
        return database.mealDao()
    }
}