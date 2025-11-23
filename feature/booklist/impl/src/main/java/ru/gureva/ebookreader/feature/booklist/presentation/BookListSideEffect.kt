package ru.gureva.ebookreader.feature.booklist.presentation

sealed interface BookListSideEffect {
    data class ShowSnackbarWithRetryButton(val message: String) : BookListSideEffect
    data class ShowSnackbar(val message: String) : BookListSideEffect
    data class NavigateToBook(val fileName: String, val title: String) : BookListSideEffect
}
