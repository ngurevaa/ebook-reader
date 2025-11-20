package ru.gureva.ebookreader.feature.auth.presentation.login

sealed interface LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent
    data class OnPasswordChange(val password: String) : LoginEvent
    data object OnPasswordVisibilityChange : LoginEvent
    data object SignIn : LoginEvent
}
