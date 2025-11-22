package ru.gureva.ebookreader.feature.booklist.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val local: Boolean
)
