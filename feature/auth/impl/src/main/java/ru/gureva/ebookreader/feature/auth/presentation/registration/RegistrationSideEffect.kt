package ru.gureva.ebookreader.feature.auth.presentation.registration

sealed interface RegistrationSideEffect {
    data class ShowSnackbar(val message: String): RegistrationSideEffect
    data class ShowSnackbarWithRetryButton(val message: String): RegistrationSideEffect
}
