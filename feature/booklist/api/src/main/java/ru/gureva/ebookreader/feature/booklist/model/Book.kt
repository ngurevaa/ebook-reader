package ru.gureva.ebookreader.feature.booklist.model

data class Book(
    val fileName: String,
    val title: String,
    val author: String,
    val isLocal: Boolean,
    val isLoading: Boolean = false
)
