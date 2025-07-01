package com.example.calories_calculator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calories_calculator.presentation.food.list.FoodListPresenter
import com.example.calories_calculator.presentation.food.list.FoodListScreen
import com.example.calories_calculator.presentation.main.MainPresenter
import com.example.calories_calculator.presentation.main.MainScreen
import javax.inject.Inject

@Composable
fun CaloriesNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.MAIN,
        modifier = modifier
    ) {
        composable(NavigationRoutes.MAIN) {
            // Получаем Presenter через Hilt в рамках этого Composable
            val mainPresenter = hiltViewModel<MainPresenterWrapper>().presenter

            MainScreen(
                presenter = mainPresenter,
                onNavigateToFoodList = {
                    navController.navigate(NavigationRoutes.FOOD_LIST)
                },
                selectedFood = navController
                    .currentBackStackEntry
                    ?.savedStateHandle
                    ?.get<String>(NavigationRoutes.SELECTED_FOOD_KEY)
            )
        }

        composable(NavigationRoutes.FOOD_LIST) {
            // Получаем Presenter через Hilt в рамках этого Composable
            val foodListPresenter = hiltViewModel<FoodListPresenterWrapper>().presenter

            FoodListScreen(
                presenter = foodListPresenter,
                onFoodSelected = { selectedFood ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(NavigationRoutes.SELECTED_FOOD_KEY, selectedFood)
                    navController.popBackStack()
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}