package com.example.calories_calculator.data.local.dao

import androidx.room.*
import com.example.calories_calculator.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE substr(dateTime, 1, 10) = :dateString")
    suspend fun getMealsByDateString(dateString: String): List<MealEntity>

    @Query("SELECT * FROM meals WHERE dateTime BETWEEN :startDateTime AND :endDateTime")
    suspend fun getMealsBetweenDates(startDateTime: String, endDateTime: String): List<MealEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity): Long

    @Update
    suspend fun updateMeal(meal: MealEntity)

    @Delete
    suspend fun deleteMeal(meal: MealEntity)
}