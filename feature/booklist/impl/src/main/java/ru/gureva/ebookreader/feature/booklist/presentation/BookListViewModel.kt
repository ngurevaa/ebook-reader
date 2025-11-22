package ru.gureva.ebookreader.feature.booklist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.core.component.KoinComponent
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.gureva.ebookreader.feature.booklist.usecase.GetAllBooksUseCase
import org.koin.core.component.inject
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.booklist.R
import ru.gureva.ebookreader.feature.booklist.usecase.DeleteBookUseCase

class BookListViewModel : ContainerHost<BookListState, BookListSideEffect>, ViewModel(), KoinComponent {
    override val container = container<BookListState, BookListSideEffect>(BookListState())

    private val resourceManager: ResourceManager by inject()
    private val getAllBooksUseCase: GetAllBooksUseCase by inject()
    private val deleteBookUseCase: DeleteBookUseCase by inject()

    fun dispatch(event: BookListEvent) {
        when (event) {
            BookListEvent.LoadBooks -> loadBooks()
            is BookListEvent.DeleteBook -> deleteBook(event.fileName)
        }
    }

    private fun deleteBook(fileName: String) = intent {
        runCatching { deleteBookUseCase(fileName) }
            .onSuccess {
                val books = state.books.toMutableList()
                val index = books.indexOfFirst { book -> book.fileName == fileName }
                books[index] = books[index].copy(local = false)

                reduce { state.copy(books = books) }
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
}
