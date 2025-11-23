package ru.gureva.ebookreader.feature.profile.presentation

sealed interface ProfileSideEffect {
    data object NavigateToLogin : ProfileSideEffect
    data class ShowSnackbar(val message: String) : ProfileSideEffect
}
