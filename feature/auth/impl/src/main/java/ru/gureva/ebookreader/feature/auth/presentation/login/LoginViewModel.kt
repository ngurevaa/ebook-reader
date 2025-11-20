package ru.gureva.ebookreader.feature.auth.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class LoginViewModel: ContainerHost<LoginState, LoginSideEffect>, ViewModel() {
    override val container = container<LoginState, LoginSideEffect>(LoginState())

    fun dispatch(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> onEmailChange(event.email)
            is LoginEvent.OnPasswordChange -> onPasswordChange(event.password)
            LoginEvent.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
        }
    }

    private fun onEmailChange(email: String) = intent {
        reduce { state.copy(email = email) }
    }

    private fun onPasswordChange(password: String) = intent {
        reduce { state.copy(password = password) }
    }

    private fun onPasswordVisibilityChange() = intent {
        reduce { state.copy(isPasswordVisible = !state.isPasswordVisible) }
    }
}
