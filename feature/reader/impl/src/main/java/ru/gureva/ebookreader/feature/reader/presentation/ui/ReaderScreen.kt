package ru.gureva.ebookreader.feature.reader.presentation.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.gureva.ebookreader.feature.reader.R
import ru.gureva.ebookreader.feature.reader.presentation.ReaderEvent
import ru.gureva.ebookreader.feature.reader.presentation.ReaderEvent.CloseReader
import ru.gureva.ebookreader.feature.reader.presentation.ReaderEvent.OpenSettings
import ru.gureva.ebookreader.feature.reader.presentation.ReaderEvent.ReadFile
import ru.gureva.ebookreader.feature.reader.presentation.ReaderEvent.SaveProgress
import ru.gureva.ebookreader.feature.reader.presentation.ReaderSideEffect
import ru.gureva.ebookreader.feature.reader.presentation.ReaderState
import ru.gureva.ebookreader.feature.reader.presentation.ReaderViewModel

@Composable
fun ReaderScreen(
    fileName: String,
    title: String,
    navigateBack: () -> Unit,
    viewModel: ReaderViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()
    val dispatch = viewModel::dispatch

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        dispatch(ReadFile(fileName, title))
    }

    val scrollState = rememberScrollState()
    Scaffold(
        topBar = { ReaderAppBar(
            state = state,
            onBack = { dispatch(CloseReader) },
            onSettings = { dispatch(OpenSettings) }
        ) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        ReaderScreenContent(state, dispatch, scrollState)
    }

    if (state.showSettings) {
        SettingsBottomSheet(state, dispatch, scrollState)
    }

    val retryLabel = stringResource(R.string.retry)
    viewModel.collectSideEffect {
        when (it) {
            is ReaderSideEffect.ShowSnackbarWithRetryButton -> {
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = retryLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        dispatch(ReadFile(fileName, title))
                    }
                }
            }
            ReaderSideEffect.NavigateBack -> navigateBack()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReaderAppBar(
    state: ReaderState,
    onBack: () -> Unit,
    onSettings: () -> Unit
) {
    TopAppBar(
        title = { Text(
            text = state.title
        ) },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            TextButton(onClick = { onSettings() }) {
                Text(
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = state.theme.textColor,
            containerColor = state.theme.backgroundColor,
            navigationIconContentColor = state.theme.textColor,
            actionIconContentColor = state.theme.textColor
        )
    )
}

@Composable
internal fun ReaderScreenContent(
    state: ReaderState,
    dispatch: (ReaderEvent) -> Unit,
    scrollState: ScrollState
) {
    LaunchedEffect(state.progress) {
        val targetValue = (scrollState.maxValue * state.progress).toInt()
        scrollState.scrollTo(targetValue)
    }

    var lastSaveTime by remember { mutableLongStateOf(0L) }
    LaunchedEffect(scrollState.value) {
        if (System.currentTimeMillis() - lastSaveTime > 1000 && scrollState.maxValue > 0) {
            val progress = scrollState.value.toFloat() / scrollState.maxValue
            dispatch(SaveProgress(progress))
            lastSaveTime = System.currentTimeMillis()
        }
    }

    Column(
        modifier = Modifier
            .background(state.theme.backgroundColor)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = state.text,
            fontSize = state.fontSize,
            color = state.theme.textColor
        )
    }
}
