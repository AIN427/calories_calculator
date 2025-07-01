package com.example.calories_calculator.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object PresentationModule {
    // Presenter'ы теперь создаются автоматически через @Inject конструкторы
}