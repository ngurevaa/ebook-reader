package ru.gureva.ebookreader.feature.auth.presentation.registration

import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.NetworkUtil
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.auth.R
import ru.gureva.ebookreader.feature.auth.usecase.CheckEmailValidityUseCase
import ru.gureva.ebookreader.feature.auth.usecase.CheckPasswordValidityUseCase
import ru.gureva.ebookreader.feature.auth.usecase.SignUpUseCase
import kotlin.getValue

class RegistrationViewModel: ContainerHost<RegistrationState, RegistrationSideEffect>, ViewModel(), KoinComponent {
    override val container = container<RegistrationState, RegistrationSideEffect>(RegistrationState())

    private val resourceManager: ResourceManager by inject()
    private val networkUtil: NetworkUtil by inject()
    private val checkEmailValidityUseCase: CheckEmailValidityUseCase by inject()
    private val checkPasswordValidityUseCase: CheckPasswordValidityUseCase by inject()
    private val signUpUseCase: SignUpUseCase by inject()

    fun dispatch(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.ChangeEmail -> onEmailChange(event.email)
            is RegistrationEvent.ChangePassword -> onPasswordChange(event.password)
            RegistrationEvent.TogglePasswordVisibility -> onPasswordVisibilityChange()
            RegistrationEvent.SignUp -> signUp()
            RegistrationEvent.ClickToLogin -> clickToLogin()
        }
    }

    private fun clickToLogin() = intent {
        postSideEffect(RegistrationSideEffect.NavigateToLogin)
    }

    private fun signUp() = intent {
        if (!networkUtil.isNetworkAvailable()) {
            postSideEffect(RegistrationSideEffect.ShowSnackbarWithRetryButton(
                resourceManager.getString(R.string.network_error))
            )
            return@intent
        }

        runCatching {
            reduce { state.copy(isLoading = true) }
            signUpUseCase(state.email, state.password)
        }
            .onSuccess {
                reduce { state.copy(isLoading = false) }
                // navigate to main screen
            }
            .onFailure { ex ->
                reduce { state.copy(isLoading = false) }
                val message = when (ex) {
                    is FirebaseAuthUserCollisionException -> R.string.user_exists_error
                    is FirebaseNetworkException -> R.string.firebase_network_error
                    is FirebaseTooManyRequestsException -> R.string.too_many_requests_error
                    else -> R.string.registration_error
                }
                postSideEffect(RegistrationSideEffect.ShowSnackbar(resourceManager.getString(message)))
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
