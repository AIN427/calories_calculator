package com.example.calories_calculator.presentation.base

interface BaseContract {

    interface View {
        // Базовые методы для View
    }

    interface Presenter<V : View> {
        fun onAttach(view: V)
        fun onCreate()
        fun onStart()
        fun onResume()
        fun onPause()
        fun onStop()
        fun onDestroy()
    }
}