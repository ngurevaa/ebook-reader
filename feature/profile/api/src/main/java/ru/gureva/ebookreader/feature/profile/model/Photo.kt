package ru.gureva.ebookreader.feature.profile.model

data class Photo(
    val data: ByteArray,
    val userId: String,
    val fileName: String
)
