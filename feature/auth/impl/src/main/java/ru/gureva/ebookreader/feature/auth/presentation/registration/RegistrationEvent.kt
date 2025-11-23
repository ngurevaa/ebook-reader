package ru.gureva.ebookreader.feature.auth.presentation.registration

sealed interface RegistrationEvent {
    data class ChangeEmail(val email: String) : RegistrationEvent
    data class ChangePassword(val password: String) : RegistrationEvent
    data object TogglePasswordVisibility : RegistrationEvent
    data object SignUp : RegistrationEvent
    data object ClickToLogin : RegistrationEvent
}
