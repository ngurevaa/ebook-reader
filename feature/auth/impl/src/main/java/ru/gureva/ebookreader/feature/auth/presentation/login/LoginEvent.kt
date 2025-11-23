package ru.gureva.ebookreader.feature.auth.presentation.login

sealed interface LoginEvent {
    data class ChangeEmail(val email: String) : LoginEvent
    data class ChangePassword(val password: String) : LoginEvent
    data object TogglePasswordVisibility : LoginEvent
    data object SignIn : LoginEvent
    data object ClickToRegistration : LoginEvent
}
