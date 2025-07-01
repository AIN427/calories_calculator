package com.example.calories_calculator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val foodId: Long,
    val weight: Double, // в граммах
    val dateTime: LocalDateTime,
    val mealType: String // завтрак, обед, ужин, перекус
)