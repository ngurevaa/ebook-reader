package ru.gureva.ebookreader.feature.bookupload.presentation

sealed interface BookUploadSideEffect {
    data class ShowSnackbar(val message: String) : BookUploadSideEffect
    data class ShowSnackbarWithRetryButton(val message: String) : BookUploadSideEffect
}
