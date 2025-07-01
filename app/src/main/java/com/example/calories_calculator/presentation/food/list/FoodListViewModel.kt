package com.example.calories_calculator.presentation.food.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calories_calculator.domain.repository.FoodRepository
import com.example.calories_calculator.presentation.base.BaseViewModel
import com.example.calories_calculator.presentation.food.model.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    private val _foodList = MutableLiveData<List<FoodItem>>()
    val foodList: LiveData<List<FoodItem>> = _foodList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadFoodList() {
        launch(
            context = kotlinx.coroutines.Dispatchers.IO,
            onSuccess = { foods: List<FoodItem> ->
                _foodList.postValue(foods)
                _error.postValue(null)
            },
            onError = { throwable ->
                _error.postValue("Error loading food list: ${throwable.message}")
            },
            onComplete = {
                _isLoading.postValue(false)
            },
            runningBlock = {
                _isLoading.postValue(true)

                // Имитация загрузки данных
                kotlinx.coroutines.delay(1500)

                // Мокаем данные
                listOf(
                    FoodItem(1, "Apple", 52, "Fresh red apple", "Fruits"),
                    FoodItem(2, "Banana", 89, "Ripe yellow banana", "Fruits"),
                    FoodItem(3, "Chicken Breast", 165, "Grilled chicken breast", "Meat"),
                    FoodItem(4, "Brown Rice", 111, "Cooked brown rice", "Grains"),
                    FoodItem(5, "Broccoli", 34, "Fresh green broccoli", "Vegetables"),
                    FoodItem(6, "Salmon", 208, "Atlantic salmon fillet", "Fish"),
                    FoodItem(7, "Greek Yogurt", 59, "Plain Greek yogurt", "Dairy"),
                    FoodItem(8, "Almonds", 579, "Raw almonds", "Nuts"),
                    FoodItem(9, "Sweet Potato", 86, "Baked sweet potato", "Vegetables"),
                    FoodItem(10, "Oatmeal", 68, "Cooked oatmeal", "Grains")
                )
            }
        )
    }
}