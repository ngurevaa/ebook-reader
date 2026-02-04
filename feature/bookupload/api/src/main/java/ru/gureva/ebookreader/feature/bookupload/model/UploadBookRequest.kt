package ru.gureva.ebookreader.feature.bookupload.model

data class UploadBookRequest(
    val filePath: String,
    val title: String,
    val author: String,
    val userId: String
)
