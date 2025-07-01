package com.example.calories_calculator.domain.usecase

import com.example.calories_calculator.data.repository.FoodRepositoryImpl
import com.example.calories_calculator.domain.model.Food
import javax.inject.Inject

class LoadInitialFoodDataUseCase @Inject constructor(
    private val foodRepository: FoodRepositoryImpl
) {

    suspend operator fun invoke(): List<Food> {
        return foodRepository.loadInitialData()
    }
}