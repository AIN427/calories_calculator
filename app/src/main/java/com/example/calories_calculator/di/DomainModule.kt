package com.example.calories_calculator.di

import com.example.calories_calculator.data.repository.FoodRepositoryImpl
import com.example.calories_calculator.domain.repository.FoodRepository
import com.example.calories_calculator.domain.usecase.GetFoodListUseCase
import com.example.calories_calculator.domain.usecase.LoadInitialFoodDataUseCase
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

    @Provides
    @Singleton
    fun provideLoadInitialFoodDataUseCase(
        foodRepository: FoodRepositoryImpl  // Конкретная реализация для метода loadInitialData
    ): LoadInitialFoodDataUseCase {
        return LoadInitialFoodDataUseCase(foodRepository)
    }
}