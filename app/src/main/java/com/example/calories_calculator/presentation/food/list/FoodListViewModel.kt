package com.example.calories_calculator.presentation.food.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.calories_calculator.data.repository.FoodRepositoryImpl
import com.example.calories_calculator.domain.usecase.GetFoodListUseCase
import com.example.calories_calculator.presentation.base.BaseViewModel
import com.example.calories_calculator.presentation.common.UiState
import com.example.calories_calculator.presentation.food.mapper.toFoodItems
import com.example.calories_calculator.presentation.food.model.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val getFoodListUseCase: GetFoodListUseCase,
    private val foodRepository: FoodRepositoryImpl
) : BaseViewModel() {

    // ✅ Одно состояние вместо нескольких LiveData
    private val _uiState = MutableLiveData<UiState<List<FoodItem>>>(UiState.Idle)
    val uiState: LiveData<UiState<List<FoodItem>>> = _uiState

    fun loadFoodList() {
        viewModelScope.launch {
            _uiState.postValue(UiState.Loading)

            try {
                // Инициализируем данные в репозитории
                foodRepository.loadInitialData()

                // Подписываемся на поток данных
                getFoodListUseCase()
                    .catch { throwable ->
                        _uiState.postValue(UiState.Error("Failed to load food list: ${throwable.message}"))
                    }
                    .collect { foods ->
                        val foodItems = foods.toFoodItems()
                        _uiState.postValue(UiState.Success(foodItems))
                    }

            } catch (throwable: Throwable) {
                _uiState.postValue(UiState.Error("Failed to load food list: ${throwable.message}"))
            }
        }
    }
}