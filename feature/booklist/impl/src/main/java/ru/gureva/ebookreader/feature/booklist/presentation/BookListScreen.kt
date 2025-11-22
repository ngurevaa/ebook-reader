package ru.gureva.ebookreader.feature.booklist.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import ru.gureva.ebookreader.core.designsystem.component.CustomTextField
import ru.gureva.ebookreader.core.designsystem.theme.AppTheme
import ru.gureva.ebookreader.feature.booklist.R
import androidx.compose.runtime.getValue

@Composable
fun BookListScreen(viewModel: BookListViewModel = koinViewModel()) {
    val state by viewModel.collectAsState()
    val dispatch = viewModel::dispatch

    Scaffold(
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            BookListScreenContent()
        }
    }

    LaunchedEffect(Unit) {
        dispatch(BookListEvent.LoadBooks)
    }
}

@Composable
internal fun BookListScreenContent() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.my_books),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = "",
            onValueChange = {},
            placeholder = stringResource(R.string.book_research)
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}
