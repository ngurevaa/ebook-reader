package ru.gureva.ebookreader.feature.bookupload.repository

import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata

interface BookRepository {
    suspend fun uploadBookToRemoteStorage(userId: String, filePath: String): String
    suspend fun saveBookMetadataToRemoteStorage(userId: String, bookMetadata: BookMetadata)
    suspend fun saveBookToLocalStorage(filePath: String, bookMetadata: BookMetadata)
}
