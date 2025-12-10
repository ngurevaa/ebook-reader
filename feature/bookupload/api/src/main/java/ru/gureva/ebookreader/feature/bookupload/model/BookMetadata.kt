package ru.gureva.ebookreader.feature.bookupload.model

data class BookMetadata(
    val filePath: String,
    val fileName: String,
    val title: String,
    val author: String,
    val userId: String
)
