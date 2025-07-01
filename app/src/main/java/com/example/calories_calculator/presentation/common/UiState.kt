package com.example.calories_calculator.presentation.common

/**
 * Базовое состояние для UI
 */
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
    data object Idle : UiState<Nothing>
}

/**
 * Расширения для удобной работы с состоянием
 */
val <T> UiState<T>.isLoading: Boolean
    get() = this is UiState.Loading

val <T> UiState<T>.isSuccess: Boolean
    get() = this is UiState.Success

val <T> UiState<T>.isError: Boolean
    get() = this is UiState.Error

val <T> UiState<T>.data: T?
    get() = (this as? UiState.Success)?.data

val <T> UiState<T>.errorMessage: String?
    get() = (this as? UiState.Error)?.message

// ✅ Удобный метод для получения отображаемого текста
fun <T> UiState<T>.getDisplayText(): String {
    return when (this) {
        is UiState.Success -> data.toString()
        is UiState.Error -> message
        else -> ""
    }
}