package ru.gureva.ebookreader.feature.bookupload.model

data class FirestoreBookMetadata(
    val title: String,
    val author: String,
    val fileUrl: String,
    val userId: String
)
