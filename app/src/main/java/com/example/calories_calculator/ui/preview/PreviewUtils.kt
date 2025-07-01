package com.example.calories_calculator.ui.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calories_calculator.ui.theme.Calories_calculatorTheme

/**
 * Базовый wrapper для превью с темой приложения
 */
@Composable
fun CaloriesPreview(
    content: @Composable () -> Unit
) {
    Calories_calculatorTheme {
        Surface {
            content()
        }
    }
}

/**
 * Стандартные отступы для превью
 */
val PreviewPadding = PaddingValues(16.dp)

/**
 * Аннотации для разных типов превью
 */
@Preview(
    name = "Light",
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "Dark",
    showBackground = true,
    showSystemUi = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
annotation class LightDarkPreviews

@Preview(
    name = "Phone",
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420"
)
@Preview(
    name = "Tablet",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
annotation class DevicePreviews

@Preview(
    name = "Small Font",
    showBackground = true,
    fontScale = 0.8f
)
@Preview(
    name = "Large Font",
    showBackground = true,
    fontScale = 1.5f
)
annotation class FontScalePreviews