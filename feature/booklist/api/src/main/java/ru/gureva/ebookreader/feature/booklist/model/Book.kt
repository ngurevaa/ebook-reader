package ru.gureva.ebookreader.feature.booklist.model

data class Book(
    val fileName: String,
    val title: String,
    val author: String,
    val local: Boolean,
    val isLoading: Boolean = false
)
