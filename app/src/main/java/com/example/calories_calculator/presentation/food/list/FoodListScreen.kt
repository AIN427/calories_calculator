package com.example.calories_calculator.presentation.food.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.calories_calculator.presentation.base.BaseScreen
import com.example.calories_calculator.presentation.common.UiState
import com.example.calories_calculator.presentation.common.data
import com.example.calories_calculator.presentation.common.errorMessage
import com.example.calories_calculator.presentation.common.isLoading
import com.example.calories_calculator.presentation.food.model.FoodItem
import com.example.calories_calculator.ui.theme.Calories_calculatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodListScreen(
    presenter: FoodListPresenter,
    onFoodSelected: (String) -> Unit = {},
    onBackPressed: () -> Unit = {},
    viewModel: FoodListViewModel = hiltViewModel()
) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current

    // Инициализируем ViewModel в презентере
    LaunchedEffect(Unit) {
        presenter.initViewModel(viewModelStoreOwner!!)
        presenter.setOnFoodSelectedCallback(onFoodSelected)
        presenter.loadFoodList()
    }

    // Наблюдаем за единым состоянием
    val uiState by viewModel.uiState.observeAsState(UiState.Idle)

    // Создаем View для презентера
    val view = remember {
        object : FoodListContract.View {
            override fun showFoodList(foods: List<FoodItem>) {
                // В Compose обновляется через ViewModel
            }

            override fun showLoading(isLoading: Boolean) {
                // В Compose обновляется через ViewModel
            }

            override fun showError(error: String) {
                // В Compose обновляется через ViewModel
            }

            override fun navigateBack() {
                onBackPressed()
            }
        }
    }

    // Подключаем презентер к view
    LaunchedEffect(Unit) {
        presenter.onAttach(view)
    }

    BaseScreen(
        presenter = presenter,
        isLoading = uiState.isLoading,
        error = uiState.errorMessage
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // TopAppBar
            TopAppBar(
                title = { Text("Select Food") },
                navigationIcon = {
                    IconButton(onClick = { presenter.onBackPressed() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )

            // Content - используем данные из состояния
            when (uiState) {
                is UiState.Success -> {
                    uiState.data?.let {
                        FoodListContent(
                            foodList = it,
                            onFoodItemClick = { foodItem ->
                                presenter.onFoodItemClicked(foodItem)
                            }
                        )
                    }
                }
                is UiState.Error -> {
                    // Ошибка уже отображается в BaseScreen
                }
                is UiState.Loading -> {
                    // Загрузка уже отображается в BaseScreen
                }
                is UiState.Idle -> {
                    // Начальное состояние
                }
            }
        }
    }
}

@Composable
private fun FoodListContent(
    foodList: List<FoodItem>,
    onFoodItemClick: (FoodItem) -> Unit
) {
    if (foodList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No food items available",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(foodList) { foodItem ->
                FoodItemCard(
                    foodItem = foodItem,
                    onClick = { onFoodItemClick(foodItem) }
                )
            }
        }
    }
}

@Composable
private fun FoodItemCard(
    foodItem: FoodItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = foodItem.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = foodItem.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Category: ${foodItem.category}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${foodItem.calories}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "cal",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// MARK: - Previews
@Preview(showBackground = true)
@Composable
private fun FoodItemCardPreview() {
    Calories_calculatorTheme {
        FoodItemCard(
            foodItem = FoodItem(
                id = 1,
                name = "Apple",
                calories = 52,
                description = "Fresh red apple",
                category = "Fruits"
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FoodListContentPreview() {
    Calories_calculatorTheme {
        FoodListContent(
            foodList = listOf(
                FoodItem(1, "Apple", 52, "Fresh red apple", "Fruits"),
                FoodItem(2, "Banana", 89, "Ripe yellow banana", "Fruits"),
                FoodItem(3, "Chicken Breast", 165, "Grilled chicken breast", "Meat")
            ),
            onFoodItemClick = {}
        )
    }
}