package ru.gureva.ebookreader.feature.reader.presentation

sealed interface ReaderEvent {
    data class ReadFile(val fileName: String) : ReaderEvent
}
