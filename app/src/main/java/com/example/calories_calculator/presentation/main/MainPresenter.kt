package com.example.calories_calculator.presentation.main

import androidx.lifecycle.ViewModelStoreOwner
import com.example.calories_calculator.presentation.base.BasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor() :
    BasePresenter<MainContract.View>(),
    MainContract.Presenter {

    private lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        // ViewModel будет инициализирован из Composable
    }

    fun initViewModel(viewModelStoreOwner: ViewModelStoreOwner) {
        viewModel = getViewModel(viewModelStoreOwner)

        // Наблюдаем за изменениями в ViewModel
        observeViewModel()
    }

    private fun observeViewModel() {
        // В реальном проекте здесь будет подписка на LiveData/StateFlow
        // Пока делаем заглушку
    }

    override fun onTestButtonClicked() {
        view?.showMessage("Test button clicked!")

        // Вызываем метод ViewModel
        if (::viewModel.isInitialized) {
            viewModel.loadTestData()
        }
    }
}