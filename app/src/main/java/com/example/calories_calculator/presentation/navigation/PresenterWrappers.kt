package com.example.calories_calculator.presentation.navigation

import androidx.lifecycle.ViewModel
import com.example.calories_calculator.presentation.food.list.FoodListPresenter
import com.example.calories_calculator.presentation.main.MainPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainPresenterWrapper @Inject constructor(
    val presenter: MainPresenter
) : ViewModel()

@HiltViewModel
class FoodListPresenterWrapper @Inject constructor(
    val presenter: FoodListPresenter
) : ViewModel()