package com.example.calories_calculator.presentation.food.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.calories_calculator.data.repository.FoodRepositoryImpl
import com.example.calories_calculator.domain.usecase.GetFoodListUseCase
import com.example.calories_calculator.presentation.base.BaseViewModel
import com.example.calories_calculator.presentation.food.mapper.toFoodItems
import com.example.calories_calculator.presentation.food.model.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val getFoodListUseCase: GetFoodListUseCase,
    private val foodRepository: FoodRepositoryImpl // Для инициализации данных
) : BaseViewModel() {

    private val _foodList = MutableLiveData<List<FoodItem>>()
    val foodList: LiveData<List<FoodItem>> = _foodList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadFoodList() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _error.postValue(null)

            try {
                // Сначала пробуем загрузить из репозитория (с инициализацией)
                foodRepository.loadInitialData()

                // Затем подписываемся на поток данных из Use Case
                getFoodListUseCase()
                    .catch { throwable ->
                        _error.postValue("Failed to load food list: ${throwable.message}")
                        _isLoading.postValue(false)
                    }
                    .collect { foods ->
                        val foodItems = foods.toFoodItems()
                        _foodList.postValue(foodItems)
                        _isLoading.postValue(false)
                    }

            } catch (throwable: Throwable) {
                _error.postValue("Failed to load food list: ${throwable.message}")
                _isLoading.postValue(false)
            }
        }
    }
}