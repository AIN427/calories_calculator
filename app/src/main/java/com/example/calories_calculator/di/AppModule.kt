package com.example.calories_calculator.di

import android.content.Context
import androidx.room.Room
import com.example.calories_calculator.data.local.database.CaloriesDatabase
import com.example.calories_calculator.data.remote.api.CaloriesApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/") // Временный URL, потом заменим
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideCaloriesApiService(retrofit: Retrofit): CaloriesApiService {
        return retrofit.create(CaloriesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCaloriesDatabase(
        @ApplicationContext context: Context
    ): CaloriesDatabase {
        return Room.databaseBuilder(
            context,
            CaloriesDatabase::class.java,
            "calories_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}