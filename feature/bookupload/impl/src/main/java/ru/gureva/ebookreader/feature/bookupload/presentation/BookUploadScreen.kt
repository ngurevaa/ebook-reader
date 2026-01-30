package ru.gureva.ebookreader.feature.bookupload.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.gureva.ebookreader.core.designsystem.component.CustomTextField
import ru.gureva.ebookreader.feature.bookupload.R
import ru.gureva.ebookreader.feature.bookupload.constants.BookFileTypes

@Composable
fun BookUploadScreen(
    viewModel: BookUploadViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()
    val dispatch = viewModel::dispatch

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            BookUploadScreenContent(state, dispatch)
        }
    }

    val retryLabel = stringResource(R.string.retry)
    viewModel.collectSideEffect {
        when (it) {
            is BookUploadSideEffect.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
            is BookUploadSideEffect.ShowSnackbarWithRetryButton -> {
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = retryLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        dispatch(BookUploadEvent.UploadBook)
                    }
                }
            }
        }
    }
}

@Composable
internal fun BookUploadScreenContent(
    state: BookUploadState,
    dispatch: (BookUploadEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = stringResource(R.string.book_upload),
        style = MaterialTheme.typography.headlineMedium
    )
    Spacer(modifier = Modifier.height(32.dp))
    GetContent(
        fileName = state.fileName,
        selectFile = { dispatch(BookUploadEvent.SelectFile(it)) }
    )
    Spacer(modifier = Modifier.height(32.dp))
    Text(
        text = stringResource(R.string.book_name),
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(16.dp))
    CustomTextField(
        value = state.bookName,
        onValueChange = { dispatch(BookUploadEvent.ChangeBookName(it)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusable()
    )
    Spacer(modifier = Modifier.height(32.dp))
    Text(
        text = stringResource(R.string.book_author),
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(16.dp))
    CustomTextField(
        value = state.bookAuthor,
        onValueChange = { dispatch(BookUploadEvent.ChangeBookAuthor(it)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        )
    )
    Spacer(modifier = Modifier.height(32.dp))
    UploadButton(
        isLoading = state.isLoading,
        isUploadingEnabled = state.isUploadingEnabled,
        upload = { dispatch(BookUploadEvent.UploadBook) }
    )
}

@Composable
internal fun UploadButton(
    isLoading: Boolean,
    isUploadingEnabled: Boolean,
    upload: () -> Unit
) {
    val context = LocalContext.current
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {}

    Button(
        onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permission = Manifest.permission.POST_NOTIFICATIONS
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                    notificationPermissionLauncher.launch(permission)
                }
            }
            upload()
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = isUploadingEnabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp)
            )
        }
        else {
            Text(
                text = stringResource(R.string.download),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
internal fun GetContent(
    fileName: String?,
    selectFile: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let { selectFile(it) }
    }

    Button(
        onClick = {
            launcher.launch(
                arrayOf(BookFileTypes.TXT, BookFileTypes.PDF, BookFileTypes.EPUB)
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.select_file),
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
    if (fileName != null) {
        Text(text = "${stringResource(R.string.selected_file)}: $fileName")
    }
}
