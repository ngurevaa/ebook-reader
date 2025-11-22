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

class BookListViewModel : ContainerHost<BookListState, BookListSideEffect>, ViewModel(), KoinComponent {
    override val container = container<BookListState, BookListSideEffect>(BookListState())

    private val getAllBooksUseCase: GetAllBooksUseCase by inject()

    fun dispatch(event: BookListEvent) {
        when (event) {
            BookListEvent.LoadBooks -> loadBooks()
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
                it.printStackTrace()
            }
    }
}
