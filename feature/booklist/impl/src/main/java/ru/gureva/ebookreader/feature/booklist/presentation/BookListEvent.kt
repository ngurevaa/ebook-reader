package ru.gureva.ebookreader.feature.booklist.presentation

sealed interface BookListEvent {
    data object LoadBooks : BookListEvent
}
