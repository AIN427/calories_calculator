package com.example.calories_calculator.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.calories_calculator.presentation.base.BaseScreen
import com.example.calories_calculator.ui.theme.Calories_calculatorTheme

@Composable
fun MainScreen(
    presenter: MainPresenter,
    onNavigateToFoodList: () -> Unit = {},
    selectedFood: String? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: MainViewModel = hiltViewModel()
) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current

    // Инициализируем ViewModel в презентере
    LaunchedEffect(Unit) {
        presenter.initViewModel(viewModelStoreOwner!!)
    }

    // Наблюдаем за состоянием
    val message by viewModel.message.observeAsState("")
    val isLoading by viewModel.isLoading.observeAsState(false)

    // Создаем View для презентера
    val view = remember {
        object : MainContract.View {
            override fun showMessage(message: String) {
                // В Compose мы обновляем состояние через ViewModel
                // Этот метод можно использовать для других действий
            }

            override fun showLoading(isLoading: Boolean) {
                // Аналогично
            }
        }
    }

    // Подключаем презентер к view
    LaunchedEffect(Unit) {
        presenter.onAttach(view)
    }

    BaseScreen(
        presenter = presenter,
        isLoading = isLoading
    ) {
        MainScreenContent(
            message = message,
            selectedFood = selectedFood,
            contentPadding = contentPadding,
            onTestButtonClick = {
                presenter.onTestButtonClicked()
            },
            onSelectFoodClick = {
                onNavigateToFoodList()
            }
        )
    }
}

@Composable
internal fun MainScreenContent(
    message: String,
    selectedFood: String?,
    contentPadding: PaddingValues,
    onTestButtonClick: () -> Unit,
    onSelectFoodClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Calories Calculator",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onTestButtonClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Test Architecture")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSelectFoodClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Food")
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedFood?.let { food ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Selected: $food",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (message.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
internal fun MainScreenContentPreview(
    message: String = "",
    selectedFood: String? = null,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onTestButtonClick: () -> Unit = {},
    onSelectFoodClick: () -> Unit = {}
) {
    MainScreenContent(
        message = message,
        selectedFood = selectedFood,
        contentPadding = contentPadding,
        onTestButtonClick = onTestButtonClick,
        onSelectFoodClick = onSelectFoodClick
    )
}
class MainScreenPreviewParameterProvider : PreviewParameterProvider<MainScreenPreviewData> {
    override val values = sequenceOf(
        MainScreenPreviewData(
            message = "",
            isLoading = false
        ),
        MainScreenPreviewData(
            message = "Success: Hello from ViewModel!",
            isLoading = false
        ),
        MainScreenPreviewData(
            message = "",
            isLoading = true
        ),
        MainScreenPreviewData(
            message = "Error: Something went wrong",
            isLoading = false
        )
    )
}

data class MainScreenPreviewData(
    val message: String,
    val isLoading: Boolean
)

// MARK: - Previews
@Preview(
    name = "Main Screen - Empty",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun MainScreenPreview() {
    Calories_calculatorTheme {
        MainScreenContentPreview()
    }
}

@Preview(
    name = "Main Screen - With Message",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun MainScreenWithMessagePreview() {
    Calories_calculatorTheme {
        MainScreenContentPreview(
            message = "Success: Hello from ViewModel!"
        )
    }
}

@Preview(
    name = "Main Screen - With Selected Food",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun MainScreenWithSelectedFoodPreview() {
    Calories_calculatorTheme {
        MainScreenContentPreview(
            selectedFood = "Apple (52 cal)",
            message = "Food selected successfully!"
        )
    }
}

@Preview(
    name = "Main Screen - Error",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun MainScreenErrorPreview() {
    Calories_calculatorTheme {
        MainScreenContentPreview(
            message = "Error: Something went wrong"
        )
    }
}

@Preview(
    name = "Main Screen - All States",
    showBackground = true,
    group = "States"
)
@Composable
private fun MainScreenAllStatesPreview(
    @PreviewParameter(MainScreenPreviewParameterProvider::class)
    previewData: MainScreenPreviewData
) {
    Calories_calculatorTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            if (previewData.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                MainScreenContentPreview(
                    message = previewData.message
                )
            }
        }
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MainScreenDarkPreview() {
    Calories_calculatorTheme {
        MainScreenContentPreview(
            message = "Success: Hello from ViewModel!"
        )
    }
}

@Preview(
    name = "Landscape",
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
private fun MainScreenLandscapePreview() {
    Calories_calculatorTheme {
        MainScreenContentPreview(
            message = "Success: Hello from ViewModel!"
        )
    }
}