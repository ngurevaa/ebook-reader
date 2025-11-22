package ru.gureva.ebookreader.feature.booklist.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import ru.gureva.ebookreader.core.designsystem.component.CustomTextField
import ru.gureva.ebookreader.feature.booklist.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.gureva.ebookreader.feature.booklist.model.Book
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import ru.gureva.ebookreader.core.designsystem.theme.AppTheme

@Composable
fun BookListScreen(viewModel: BookListViewModel = koinViewModel()) {
    val state by viewModel.collectAsState()
    val dispatch = viewModel::dispatch

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            BookListScreenContent(state, dispatch)
        }
    }

    LaunchedEffect(Unit) {
        dispatch(BookListEvent.LoadBooks)
    }

    val retryLabel = stringResource(R.string.retry)
    viewModel.collectSideEffect {
        when (it) {
//            is BookUploadSideEffect.ShowSnackbar -> {
//                coroutineScope.launch {
//                    snackbarHostState.showSnackbar(it.message)
//                }
//            }
            is BookListSideEffect.ShowSnackbarWithRetryButton -> {
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = retryLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        dispatch(BookListEvent.LoadBooks)
                    }
                }
            }
        }
    }
}

@Composable
internal fun BookListScreenContent(state: BookListState, dispatch: (BookListEvent) -> Unit) {
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
    BookList(state.books)
}

@Composable
internal fun BookList(
    books: List<Book>
) {
    if (books.isEmpty()) {
        Text(text = stringResource(R.string.load_your_first_book))
        return
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = books,
            key = { book -> book.id }
        ) {
            BookItem(it)
        }
    }
}

@Composable
internal fun BookItem(
    book: Book
) {
    Surface(
//        shadowElevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = R.drawable.book_placeholder,
                contentDescription = null,
                modifier = Modifier.size(width = 40.dp, height = 60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = book.author)
            }
            Spacer(modifier = Modifier.weight(1f))
            if (book.local) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null
                )
            }
            else {
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = null
                )
            }
        }
    }
}
