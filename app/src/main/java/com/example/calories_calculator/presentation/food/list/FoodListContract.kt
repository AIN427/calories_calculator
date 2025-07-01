package com.example.calories_calculator.presentation.food.list

import com.example.calories_calculator.presentation.base.BaseContract
import com.example.calories_calculator.presentation.food.model.FoodItem

class FoodListContract {

    interface View : BaseContract.View {
        fun showFoodList(foods: List<FoodItem>)
        fun showLoading(isLoading: Boolean)
        fun showError(error: String)
        fun navigateBack()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadFoodList()
        fun onFoodItemClicked(foodItem: FoodItem)
        fun onBackPressed()
    }
}