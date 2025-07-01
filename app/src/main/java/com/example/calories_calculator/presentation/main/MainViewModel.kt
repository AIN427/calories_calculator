package com.example.calories_calculator.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calories_calculator.domain.repository.FoodRepository
import com.example.calories_calculator.presentation.base.BaseViewModel
import com.example.calories_calculator.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    // ✅ UiState только для асинхронных операций
    private val _testDataState = MutableLiveData<UiState<String>>(UiState.Idle)
    val testDataState: LiveData<UiState<String>> = _testDataState

    // ✅ Простое значение для выбранной еды
    private val _selectedFood = MutableLiveData<String?>()
    val selectedFood: LiveData<String?> = _selectedFood

    fun loadTestData() {
        _testDataState.postValue(UiState.Loading)

        launch(
            context = kotlinx.coroutines.Dispatchers.IO,
            onSuccess = { result: String ->
                _testDataState.postValue(UiState.Success(result))
            },
            onError = { throwable ->
                _testDataState.postValue(UiState.Error("Error: ${throwable.message}"))
            },
            runningBlock = {
                // Имитация работы с данными
                kotlinx.coroutines.delay(2000)
                "Hello from ViewModel!"
            }
        )
    }

    fun setSelectedFood(food: String?) {
        _selectedFood.postValue(food)
    }
}