package ru.gureva.ebookreader.feature.bookupload.model

import java.util.Date

data class BookMetadata(
    val title: String,
    val author: String,
    val fileName: String,
    val creationDate: Date
)
