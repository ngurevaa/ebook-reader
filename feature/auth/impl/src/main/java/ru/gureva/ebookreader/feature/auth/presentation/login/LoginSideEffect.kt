package ru.gureva.ebookreader.feature.auth.presentation.login

sealed interface LoginSideEffect {
    data class ShowSnackbarWithRetryButton(val message: String): LoginSideEffect
    data class ShowSnackbar(val message: String): LoginSideEffect
    data object NavigateToRegistration : LoginSideEffect
    data object NavigateToBookList : LoginSideEffect
}
