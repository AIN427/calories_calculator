package com.example.calories_calculator.di

import com.example.calories_calculator.data.datasource.FoodRemoteDataSourceImpl
import com.example.calories_calculator.domain.datasource.FoodRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindFoodRemoteDataSource(
        foodRemoteDataSourceImpl: FoodRemoteDataSourceImpl
    ): FoodRemoteDataSource
}