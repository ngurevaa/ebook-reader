package ru.gureva.ebookreader.feature.auth.presentation.registration

sealed interface RegistrationEvent {
    data class OnEmailChange(val email: String): RegistrationEvent
    data class OnPasswordChange(val password: String): RegistrationEvent
    data object OnPasswordVisibilityChange: RegistrationEvent
    data object SignUp : RegistrationEvent
}
