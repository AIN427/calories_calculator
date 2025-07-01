package com.example.calories_calculator.domain.usecase

import com.example.calories_calculator.domain.model.Food
import com.example.calories_calculator.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFoodListUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {

    operator fun invoke(): Flow<List<Food>> {
        return foodRepository.getAllFoods()
    }
}