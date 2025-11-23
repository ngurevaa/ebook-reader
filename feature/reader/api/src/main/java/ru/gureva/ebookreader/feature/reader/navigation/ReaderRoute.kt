package ru.gureva.ebookreader.feature.reader.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ReaderRoute(
    val fileName: String
)
