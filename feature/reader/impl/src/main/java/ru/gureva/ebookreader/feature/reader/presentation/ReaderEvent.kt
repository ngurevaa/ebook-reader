package ru.gureva.ebookreader.feature.reader.presentation

import androidx.compose.ui.unit.TextUnit

sealed interface ReaderEvent {
    data class ReadFile(val fileName: String, val title: String) : ReaderEvent
    data object OpenSettings : ReaderEvent
    data object CloseSettings : ReaderEvent
    data class ChangeFontSize(val size: TextUnit) : ReaderEvent
    data class ChangeReadingTheme(val theme: ReadingTheme) : ReaderEvent
    data class SaveProgress(val progress: Float) : ReaderEvent
    data object CloseReader : ReaderEvent
}
