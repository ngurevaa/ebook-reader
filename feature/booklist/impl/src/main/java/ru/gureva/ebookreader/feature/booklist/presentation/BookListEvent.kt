package ru.gureva.ebookreader.feature.booklist.presentation

sealed interface BookListEvent {
    data object LoadBooks : BookListEvent
    data class DeleteBook(val fileName: String) : BookListEvent
    data class DownloadBook(val fileUrl: String) : BookListEvent
    data class SearchBooks(val search: String) : BookListEvent
    data class OpenBook(val fileName: String) : BookListEvent
}
