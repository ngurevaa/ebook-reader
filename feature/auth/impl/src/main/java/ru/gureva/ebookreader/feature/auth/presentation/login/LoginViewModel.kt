package ru.gureva.ebookreader.feature.auth.presentation.login

import org.koin.core.component.inject
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.auth.R
import ru.gureva.ebookreader.feature.auth.usecase.CheckEmailValidityUseCase
import ru.gureva.ebookreader.feature.auth.usecase.CheckPasswordValidityUseCase

class LoginViewModel: ContainerHost<LoginState, LoginSideEffect>, ViewModel(), KoinComponent {
    override val container = container<LoginState, LoginSideEffect>(LoginState())

    private val resourceManager: ResourceManager by inject()
    private val checkEmailValidityUseCase: CheckEmailValidityUseCase by inject()
    private val checkPasswordValidityUseCase: CheckPasswordValidityUseCase by inject()

    fun dispatch(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> onEmailChange(event.email)
            is LoginEvent.OnPasswordChange -> onPasswordChange(event.password)
            LoginEvent.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
        }
    }

    private fun onEmailChange(email: String) = intent {
        reduce { state.copy(email = email) }
        if (!checkEmailValidityUseCase(email)) {
            reduce { state.copy(emailError = resourceManager.getString(R.string.email_error)) }
        }
        else {
            reduce { state.copy(emailError = null) }
        }
    }

    private fun onPasswordChange(password: String) = intent {
        reduce { state.copy(password = password) }
        if (!checkPasswordValidityUseCase(password)) {
            reduce { state.copy(passwordError = resourceManager.getString(R.string.password_error)) }
        }
        else {
            reduce { state.copy(passwordError = null) }
        }
    }

    private fun onPasswordVisibilityChange() = intent {
        reduce { state.copy(isPasswordVisible = !state.isPasswordVisible) }
    }
}
