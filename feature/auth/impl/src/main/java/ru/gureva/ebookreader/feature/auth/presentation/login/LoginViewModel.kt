package ru.gureva.ebookreader.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.NetworkUtil
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.auth.R
import ru.gureva.ebookreader.feature.auth.usecase.CheckEmailValidityUseCase
import ru.gureva.ebookreader.feature.auth.usecase.CheckPasswordValidityUseCase
import ru.gureva.ebookreader.feature.auth.usecase.SignInUseCase

class LoginViewModel: ContainerHost<LoginState, LoginSideEffect>, ViewModel(), KoinComponent {
    override val container = container<LoginState, LoginSideEffect>(LoginState())

    private val resourceManager: ResourceManager by inject()
    private val networkUtil: NetworkUtil by inject()
    private val checkEmailValidityUseCase: CheckEmailValidityUseCase by inject()
    private val checkPasswordValidityUseCase: CheckPasswordValidityUseCase by inject()
    private val signInUseCase: SignInUseCase by inject()

    fun dispatch(event: LoginEvent) {
        when (event) {
            is LoginEvent.ChangeEmail -> onEmailChange(event.email)
            is LoginEvent.ChangePassword -> onPasswordChange(event.password)
            LoginEvent.TogglePasswordVisibility -> onPasswordVisibilityChange()
            LoginEvent.SignIn -> signIn()
            LoginEvent.ClickToRegistration -> clickToRegistration()
        }
    }

    private fun clickToRegistration() = intent {
        postSideEffect(LoginSideEffect.NavigateToRegistration)
    }

    private fun signIn() = intent {
        if (!networkUtil.isNetworkAvailable()) {
            postSideEffect(LoginSideEffect.ShowSnackbarWithRetryButton(
                resourceManager.getString(R.string.network_error))
            )
            return@intent
        }

        runCatching {
            reduce { state.copy(isLoading = true) }
            signInUseCase(state.email, state.password)
        }
            .onSuccess {
                reduce { state.copy(isLoading = false) }
                postSideEffect(LoginSideEffect.NavigateToBookList)
            }
            .onFailure { ex ->
                reduce { state.copy(isLoading = false) }
                val message = when (ex) {
                    is FirebaseAuthInvalidCredentialsException -> R.string.invalid_credentials_error
                    is FirebaseNetworkException -> R.string.firebase_network_error
                    is FirebaseTooManyRequestsException -> R.string.too_many_requests_error
                    else -> R.string.registration_error
                }
                postSideEffect(LoginSideEffect.ShowSnackbar(resourceManager.getString(message)))
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
