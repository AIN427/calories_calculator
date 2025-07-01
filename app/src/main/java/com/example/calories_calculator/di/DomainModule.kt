package com.example.calories_calculator.di

import com.example.calories_calculator.domain.repository.FoodRepository
import com.example.calories_calculator.domain.usecase.GetFoodListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetFoodListUseCase(
        foodRepository: FoodRepository
    ): GetFoodListUseCase {
        return GetFoodListUseCase(foodRepository)
    }
}