package ru.gureva.ebookreader.feature.booklist.model

data class FirestoreBookMetadata(
    val title: String,
    val author: String,
    val fileUrl: String,
    val userId: String
)
