package ru.gureva.ebookreader.feature.booklist.presentation

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.gureva.ebookreader.core.designsystem.component.CustomTextField
import ru.gureva.ebookreader.core.ui.noRippleClickable
import ru.gureva.ebookreader.feature.booklist.R
import ru.gureva.ebookreader.feature.booklist.model.Book

@Composable
fun BookListScreen(
    navigateToBook: (String, String) -> Unit,
    viewModel: BookListViewModel = koinViewModel()
) {
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
            is BookListSideEffect.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
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
            is BookListSideEffect.NavigateToBook -> navigateToBook(it.fileName, it.title)
        }
    }
}

@Composable
internal fun BookListScreenContent(
    state: BookListState,
    dispatch: (BookListEvent) -> Unit
) {
    Spacer(modifier = Modifier.height(24.dp))
    Header(state.isSynchronized)
    Spacer(modifier = Modifier.height(16.dp))
    Search(
        search = state.search,
        onSearchChange = { dispatch(BookListEvent.SearchBooks(it)) }
    )
    Spacer(modifier = Modifier.height(16.dp))
    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp)
            )
        }
    }
    else {
        BookList(
            books = if (state.search.isEmpty()) state.books else state.searchBooks,
            onDelete = { dispatch(BookListEvent.DeleteBook(it)) },
            onDownload = { dispatch(BookListEvent.DownloadBook(it)) },
            openBook = { fileName, title -> dispatch(BookListEvent.OpenBook(fileName, title)) }
        )
    }
}

@Composable
internal fun Search(
    search: String,
    onSearchChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    CustomTextField(
        value = search,
        onValueChange = { onSearchChange(it) },
        placeholder = stringResource(R.string.book_research),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        trailingIcon = {
            if (search.isNotBlank()) {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = null,
                    modifier = Modifier.noRippleClickable {
                        onSearchChange("")
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            }
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusable()
    )
}

@Composable
internal fun Header(isSynchronized: Boolean) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.my_books),
            style = MaterialTheme.typography.headlineMedium
        )
        if (!isSynchronized) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
internal fun BookList(
    books: List<Book>,
    onDelete: (String) -> Unit,
    onDownload: (String) -> Unit,
    openBook: (String, String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(
            items = books,
            key = { book -> book.fileName }
        ) {
            BookItem(
                book = it,
                onDelete = onDelete,
                onDownload = onDownload,
                openBook = openBook,
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
internal fun BookItem(
    book: Book,
    onDelete: (String) -> Unit,
    onDownload: (String) -> Unit,
    openBook: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.noRippleClickable {
            if (book.isLocal) openBook(book.fileName, book.title)
        }
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
            if (book.isLocal) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    modifier = Modifier.noRippleClickable {
                        onDelete(book.fileName)
                    },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            else if (book.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
            else {
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = null,
                    modifier = Modifier.noRippleClickable {
                        onDownload(book.fileName)
                    },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
