package ru.gureva.ebookreader.feature.auth.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import ru.gureva.ebookreader.feature.auth.R
import ru.gureva.ebookreader.feature.auth.presentation.common.EmailField
import ru.gureva.ebookreader.feature.auth.presentation.common.PasswordField

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()
    val dispatch = viewModel::dispatch

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.log_into_account),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    Button(onClick = {  }) {
                        Text(text = stringResource(R.string.registration))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            LoginScreenContent(state, dispatch)
        }
    }
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
            onEmailChange = { dispatch(LoginEvent.OnEmailChange(it)) }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.password),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(
            password = state.password,
            onPasswordChange = { dispatch(LoginEvent.OnPasswordChange(it)) },
            isPasswordVisible = state.isPasswordVisible,
            onPasswordVisibilityChange = { dispatch(LoginEvent.OnPasswordVisibilityChange) }
        )
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.log_in))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Spacer(modifier = Modifier.weight(1f))
    }
}
