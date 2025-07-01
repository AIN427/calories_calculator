package com.example.calories_calculator.data.remote.api

import retrofit2.http.GET

interface CaloriesApiService {

    // Пока заглушка, потом добавим реальные эндпоинты
    @GET("test")
    suspend fun getTestData(): String
}