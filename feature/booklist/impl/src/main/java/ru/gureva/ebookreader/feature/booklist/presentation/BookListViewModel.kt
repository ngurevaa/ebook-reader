package ru.gureva.ebookreader.feature.booklist.presentation

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.booklist.R
import ru.gureva.ebookreader.feature.booklist.model.Book
import ru.gureva.ebookreader.feature.booklist.usecase.DeleteBookUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.DownloadBookUseCase
import ru.gureva.ebookreader.feature.booklist.usecase.GetAllBooksUseCase

class BookListViewModel : ContainerHost<BookListState, BookListSideEffect>, ViewModel(), KoinComponent {
    override val container = container<BookListState, BookListSideEffect>(BookListState())

    private val resourceManager: ResourceManager by inject()
    private val getAllBooksUseCase: GetAllBooksUseCase by inject()
    private val deleteBookUseCase: DeleteBookUseCase by inject()
    private val downloadBookUseCase: DownloadBookUseCase by inject()

    fun dispatch(event: BookListEvent) {
        when (event) {
            BookListEvent.LoadBooks -> loadBooks()
            is BookListEvent.DeleteBook -> deleteBook(event.fileName)
            is BookListEvent.DownloadBook -> downloadBook(event.fileUrl)
            is BookListEvent.SearchBooks -> searchBooks(event.search)
        }
    }

    private fun searchBooks(search: String) = intent {
        reduce { state.copy(search = search) }
        if (state.search.isBlank()) {
            reduce { state.copy(searchBooks = listOf()) }
            return@intent
        }

        val searchBooks = state.books.filter { book ->
            book.local == true
                    && (book.author.lowercase().contains(state.search.lowercase())
                            || book.title.lowercase().contains(state.search.lowercase()))
        }
        reduce { state.copy(searchBooks = searchBooks) }
    }

    private fun downloadBook(fileUrl: String) = intent {
        runCatching {
            updateBookByUrl(fileUrl) { it.copy(isLoading = true) }
            downloadBookUseCase(fileUrl)
        }
            .onSuccess {
                updateBookByUrl(fileUrl) { it.copy(local = true, isLoading = false) }
                postSideEffect(BookListSideEffect.ShowSnackbar(
                    resourceManager.getString(R.string.book_successfully_downloaded)
                ))
            }
            .onFailure {
                updateBookByUrl(fileUrl) { it.copy(isLoading = false) }
                postSideEffect(BookListSideEffect.ShowSnackbar(
                    resourceManager.getString(R.string.book_downloading_error)
                ))
            }
    }

    private fun deleteBook(fileName: String) = intent {
        runCatching { deleteBookUseCase(fileName) }
            .onSuccess {
                updateBookByName(fileName) { it.copy(local = false) }
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
        val userId = Firebase.auth.currentUser?.uid
            ?: return@intent

        runCatching {
            getAllBooksUseCase(userId)
        }
            .onSuccess {
                reduce { state.copy(books = it) }
            }
            .onFailure {
                postSideEffect(BookListSideEffect.ShowSnackbarWithRetryButton(
                    resourceManager.getString(R.string.book_loading_error)
                ))
            }
    }

    private fun updateBookByUrl(fileUrl: String, update: (Book) -> Book) = intent {
        val books = state.books.toMutableList()
        val index = books.indexOfFirst { it.fileUrl == fileUrl }
        if (index != -1) {
            books[index] = update(books[index])
            reduce { state.copy(books = books) }
        }
    }

    private fun updateBookByName(fileName: String, update: (Book) -> Book) = intent {
        val books = state.books.toMutableList()
        val index = books.indexOfFirst { it.fileName == fileName }
        if (index != -1) {
            books[index] = update(books[index])
            reduce { state.copy(books = books) }
        }
    }
}
