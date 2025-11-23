package ru.gureva.ebookreader.feature.reader.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class ReaderState(
    val fileName: String = "",
    val title: String = "",
    val text: String = "",
    val showSettings: Boolean = false,
    val fontSize: TextUnit = 16.sp,
    val theme: ReadingTheme = ReadingTheme.LIGHT,
    val progress: Float = 0f
)

enum class ReadingTheme(
    val backgroundColor: Color,
    val textColor: Color
) {
    LIGHT(
        backgroundColor = Color.White,
        textColor = Color.Black
    ),
    DARK(
        backgroundColor = Color.Black,
        textColor = Color.White
    )
}
