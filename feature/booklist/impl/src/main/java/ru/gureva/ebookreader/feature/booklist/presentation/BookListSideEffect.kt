package ru.gureva.ebookreader.feature.booklist.presentation

sealed interface BookListSideEffect {
    data class ShowSnackbarWithRetryButton(val message: String) : BookListSideEffect
    data class ShowSnackbar(val message: String) : BookListSideEffect
}
