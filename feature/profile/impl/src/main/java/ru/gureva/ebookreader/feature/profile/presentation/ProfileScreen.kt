package ru.gureva.ebookreader.feature.profile.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.gureva.ebookreader.core.designsystem.component.CustomTextField
import ru.gureva.ebookreader.core.ui.noRippleClickable
import ru.gureva.ebookreader.feature.profile.R

@Composable
fun ProfileScreen(
    navigateToLogin: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()
    val dispatch = viewModel::dispatch

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        dispatch(ProfileEvent.LoadProfileData)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        ProfileScreenContent(state, dispatch)
    }

    viewModel.collectSideEffect {
        when (it) {
            ProfileSideEffect.NavigateToLogin -> navigateToLogin()
            is ProfileSideEffect.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }
}

@Composable
internal fun ProfileScreenContent(
    state: ProfileState,
    dispatch: (ProfileEvent) -> Unit
) {
    var showImagePicker by remember { mutableStateOf(false) }

    if (showImagePicker) ImagePicker(onImageSelected = {
        showImagePicker = false
        dispatch(ProfileEvent.SelectImage(it))
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.profile),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(20.dp))

        AsyncImage(
            model = if (state.imageUrl.isNotEmpty()) state.imageUrl else R.drawable.user_placeholder,
            contentDescription = null,
            modifier = Modifier
                .size(175.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
                .noRippleClickable { if (state.isEditMode) showImagePicker = true },
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.user_placeholder)
        )

        Spacer(modifier = Modifier.height(20.dp))
        UsernameField(state, dispatch)
        Spacer(modifier = Modifier.height(12.dp))
        EmailField(state, dispatch)
        Spacer(modifier = Modifier.weight(1f))

        val title = if (state.isEditMode) stringResource(R.string.save_changes)
            else stringResource(R.string.edit_profile)
        val onClick = { dispatch(if (state.isEditMode) ProfileEvent.SaveChanges else ProfileEvent.EditProfile) }
        EditProfileButton(title, onClick)

        Spacer(modifier = Modifier.height(8.dp))
        LogoutButton(onClick = { dispatch(ProfileEvent.Logout) })
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
internal fun ImagePicker(
    onImageSelected: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    LaunchedEffect(Unit) {
        launcher.launch("image/*")
    }
}

@Composable
internal fun EditProfileButton(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
internal fun LogoutButton(
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.log_out),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
internal fun EmailField(
    state: ProfileState,
    dispatch: (ProfileEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    Text(
        text = "${stringResource(R.string.email)}: ",
        style = MaterialTheme.typography.titleMedium,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Box {
        CustomTextField(
            value = state.editedEmail,
            onValueChange = { dispatch(ProfileEvent.UpdateEmail(it)) },
            modifier = Modifier
                .alpha(if (state.isEditMode) 1f else 0f)
                .focusRequester(focusRequester)
                .focusable(),
            enabled = state.isEditMode
        )
        Text(
            text = state.email,
            modifier = Modifier
                .alpha(if (state.isEditMode) 0f else 1f)
        )
    }
}

@Composable
internal fun UsernameField(
    state: ProfileState,
    dispatch: (ProfileEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    Text(
        text = "${stringResource(R.string.username)}: ",
        style = MaterialTheme.typography.titleMedium,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Box {
        CustomTextField(
            value = state.editedUsername,
            onValueChange = { dispatch(ProfileEvent.UpdateUsername(it)) },
            modifier = Modifier
                .alpha(if (state.isEditMode) 1f else 0f)
                .focusRequester(focusRequester)
                .focusable(),
            enabled = state.isEditMode
        )
        Text(
            text = state.username,
            modifier = Modifier
                .alpha(if (state.isEditMode) 0f else 1f)
        )
    }
}
