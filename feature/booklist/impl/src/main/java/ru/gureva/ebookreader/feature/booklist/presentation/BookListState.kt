package ru.gureva.ebookreader.feature.booklist.presentation

import ru.gureva.ebookreader.feature.booklist.model.Book

data class BookListState(
    val search: String = "",
    val books: List<Book> = listOf(),
    val searchBooks: List<Book> = listOf(),
    val isLoading: Boolean = false
)
