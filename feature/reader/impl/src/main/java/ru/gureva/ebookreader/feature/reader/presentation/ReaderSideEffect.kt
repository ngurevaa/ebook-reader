package ru.gureva.ebookreader.feature.reader.presentation

sealed interface ReaderSideEffect {
    data class ShowSnackbarWithRetryButton(val message: String) : ReaderSideEffect
    data object NavigateBack : ReaderSideEffect
}
