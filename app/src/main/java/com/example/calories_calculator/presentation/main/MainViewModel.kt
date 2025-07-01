package com.example.calories_calculator.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calories_calculator.domain.repository.FoodRepository
import com.example.calories_calculator.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadTestData() {
        _isLoading.postValue(true)

        launch(
            context = kotlinx.coroutines.Dispatchers.IO,
            onSuccess = { result: String ->
                _message.postValue("Success: $result")
            },
            onError = { throwable ->
                _message.postValue("Error: ${throwable.message}")
            },
            onComplete = {
                _isLoading.postValue(false)
            },
            runningBlock = {
                // Имитация работы с данными
                kotlinx.coroutines.delay(2000)
                "Hello from ViewModel!"
            }
        )
    }
}