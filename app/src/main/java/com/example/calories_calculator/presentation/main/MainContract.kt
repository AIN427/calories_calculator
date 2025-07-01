package com.example.calories_calculator.presentation.main

import com.example.calories_calculator.presentation.base.BaseContract

class MainContract {

    interface View : BaseContract.View {
        fun showMessage(message: String)
        fun showLoading(isLoading: Boolean)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onTestButtonClicked()
    }
}