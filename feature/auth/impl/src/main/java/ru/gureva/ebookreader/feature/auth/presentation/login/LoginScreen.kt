package ru.gureva.ebookreader.feature.auth.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.gureva.ebookreader.feature.auth.R
import ru.gureva.ebookreader.feature.auth.presentation.common.EmailField
import ru.gureva.ebookreader.feature.auth.presentation.common.Error
import ru.gureva.ebookreader.feature.auth.presentation.common.PasswordField

@Composable
fun LoginScreen(
    navigateToRegistration: () -> Unit,
    navigateToBookList: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()
    val dispatch = viewModel::dispatch

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { LoginTopAppBar(
            clickToRegistration = { dispatch(LoginEvent.ClickToRegistration) }
        ) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            LoginScreenContent(state, dispatch)
        }
    }

    val retryLabel = stringResource(R.string.retry)
    viewModel.collectSideEffect {
        when (it) {
            is LoginSideEffect.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
            is LoginSideEffect.ShowSnackbarWithRetryButton -> {
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = retryLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        dispatch(LoginEvent.SignIn)
                    }
                }
            }

            LoginSideEffect.NavigateToBookList -> navigateToBookList()
            LoginSideEffect.NavigateToRegistration -> navigateToRegistration()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginTopAppBar(
    clickToRegistration: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.log_into_account),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            Button(onClick = { clickToRegistration() }) {
                Text(text = stringResource(R.string.registration))
            }
        }
    )
}

@Composable
internal fun LoginScreenContent(
    state: LoginState,
    dispatch: (LoginEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            text = stringResource(R.string.email),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        EmailField(
            email = state.email,
            onEmailChange = { dispatch(LoginEvent.ChangeEmail(it)) }
        )
        Error(
            visible = state.emailError != null,
            error = state.emailError.toString(),
            modifier = Modifier.heightIn(min = 48.dp)
        )
        Text(
            text = stringResource(R.string.password),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(
            password = state.password,
            onPasswordChange = { dispatch(LoginEvent.ChangePassword(it)) },
            isPasswordVisible = state.isPasswordVisible,
            onPasswordVisibilityChange = { dispatch(LoginEvent.TogglePasswordVisibility) }
        )
        Error(
            visible = state.passwordError != null,
            error = state.passwordError.toString(),
            modifier = Modifier.heightIn(min = 100.dp)
        )
        Button(
            onClick = { dispatch(LoginEvent.SignIn) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isLoginEnabled
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp)
                )
            }
            else {
                Text(text = stringResource(R.string.log_in))
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Spacer(modifier = Modifier.weight(1f))
    }
}
