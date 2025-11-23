package ru.gureva.ebookreader.feature.reader.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReaderScreen(
    fileName: String,
    viewModel: ReaderViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()
    val dispatch = viewModel::dispatch

    LaunchedEffect(Unit) {
        dispatch(ReaderEvent.ReadFile(fileName))
    }

    ReaderScreenContent(state, dispatch)
}

@Composable
internal fun ReaderScreenContent(
    state: ReaderState,
    dispatch: (ReaderEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = state.text)
    }
}
