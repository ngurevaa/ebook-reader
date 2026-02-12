package ru.gureva.ebookreader.feature.booklist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.booklist.R
import ru.gureva.ebookreader.feature.booklist.usecase.DeleteBookUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.DownloadBookUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.GetAllBooksUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.SyncBooksUseCase

class BookListViewModel : ContainerHost<BookListState, BookListSideEffect>, ViewModel(), KoinComponent {
    override val container = container<BookListState, BookListSideEffect>(BookListState())

    private val resourceManager: ResourceManager by inject()
    private val getAllBooksUseCase: GetAllBooksUseCase by inject()
    private val deleteBookUseCase: DeleteBookUseCase by inject()
    private val downloadBookUseCase: DownloadBookUseCase by inject()
    private val syncBooksUseCase: SyncBooksUseCase by inject()

    fun dispatch(event: BookListEvent) {
        when (event) {
            BookListEvent.LoadBooks -> loadBooks()
            is BookListEvent.DeleteBook -> deleteBook(event.fileName)
            is BookListEvent.DownloadBook -> downloadBook(event.fileUrl)
            is BookListEvent.SearchBooks -> searchBooks(event.search)
            is BookListEvent.OpenBook -> openBook(event.fileName, event.title)
        }
    }

    private fun openBook(fileName: String, title: String) = intent {
        postSideEffect(BookListSideEffect.NavigateToBook(fileName, title))
    }

    private fun searchBooks(search: String) = intent {
        reduce { state.copy(search = search) }
        if (state.search.isBlank()) {
            reduce { state.copy(searchBooks = listOf()) }
            return@intent
        }

        val searchBooks = state.books.filter { book ->
            (book.author.lowercase().contains(state.search.lowercase())
                    || book.title.lowercase().contains(state.search.lowercase()))
        }
        reduce { state.copy(searchBooks = searchBooks) }
    }

    private fun downloadBook(fileName: String) = intent {
        updateBookDownloadingState(fileName, true)
        val userId = Firebase.auth.currentUser?.uid!!
        runCatching { downloadBookUseCase(userId, fileName) }
            .onSuccess {
                updateBookDownloadingState(fileName, false)
                postSideEffect(BookListSideEffect.ShowSnackbar(
                    resourceManager.getString(R.string.book_successfully_downloaded)
                ))
            }
            .onFailure {
                updateBookDownloadingState(fileName, false)
                postSideEffect(BookListSideEffect.ShowSnackbar(
                    resourceManager.getString(R.string.book_downloading_error)
                ))
            }
    }

    private fun updateBookDownloadingState(fileName: String, downloadingState: Boolean) = intent {
        val books = state.books.toMutableList()
        val index = books.indexOfFirst { it.fileName == fileName }
        if (index != -1) {
            books[index] = books[index].copy(isLoading = downloadingState)
            reduce { state.copy(books = books) }
        }
    }

    private fun deleteBook(fileName: String) = intent {
        runCatching { deleteBookUseCase(fileName) }
            .onSuccess {
                postSideEffect(BookListSideEffect.ShowSnackbar(
                    resourceManager.getString(R.string.book_successfully_deleted)
                ))
            }
            .onFailure {
                postSideEffect(BookListSideEffect.ShowSnackbar(
                    resourceManager.getString(R.string.book_deleting_error)
                ))
            }
    }

    private fun loadBooks() = intent {
        reduce { state.copy(isLoading = true) }

        val userId = Firebase.auth.currentUser?.uid!!
        viewModelScope.launch {
            reduce { state.copy(isSynchronized = false) }
            syncBooksUseCase(userId)
            reduce { state.copy(isSynchronized = true) }
        }

        getAllBooksUseCase()
            .catch {
                reduce { state.copy(isLoading = false) }
                postSideEffect(BookListSideEffect.ShowSnackbarWithRetryButton(
                    resourceManager.getString(R.string.book_loading_error)
                ))
            }
            .collect { books ->
                reduce { state.copy(books = books, isLoading = false) }
            }
    }
}
