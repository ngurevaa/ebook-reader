package ru.gureva.ebookreader.feature.auth.presentation.login

data class LoginState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
) {
    val isLoginEnabled: Boolean
        get() = !isLoading
                && emailError == null
                && passwordError == null
                && email.isNotBlank()
                && password.isNotBlank()
}
