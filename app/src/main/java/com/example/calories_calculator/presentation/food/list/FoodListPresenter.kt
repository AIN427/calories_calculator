package com.example.calories_calculator.presentation.food.list

import androidx.lifecycle.ViewModelStoreOwner
import com.example.calories_calculator.presentation.base.BasePresenter
import com.example.calories_calculator.presentation.food.model.FoodItem
import javax.inject.Inject

class FoodListPresenter @Inject constructor() :
    BasePresenter<FoodListContract.View>(),
    FoodListContract.Presenter {

    private lateinit var viewModel: FoodListViewModel

    override fun onCreate() {
        super.onCreate()
        // ViewModel будет инициализирован из Composable
    }

    fun initViewModel(viewModelStoreOwner: ViewModelStoreOwner) {
        viewModel = getViewModel(viewModelStoreOwner)
        observeViewModel()
    }

    private fun observeViewModel() {
        // В реальном проекте здесь будет подписка на LiveData/StateFlow
        // Пока делаем заглушку
    }

    override fun loadFoodList() {
        if (::viewModel.isInitialized) {
            viewModel.loadFoodList()
        }
    }

    override fun onFoodItemClicked(foodItem: FoodItem) {
        // ✅ Просто говорим View, что элемент выбран
        // View сама решает что делать (навигация, обновление состояния и т.д.)
        view?.onItemSelected(foodItem)
    }

    override fun onBackPressed() {
        view?.navigateBack()
    }
}