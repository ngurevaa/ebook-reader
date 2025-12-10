package ru.gureva.ebookreader.feature.bookupload.repository

import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata

interface BookRepository {
    suspend fun uploadBookToRemoteStorage(bookMetadata: BookMetadata): String
    suspend fun saveBookMetadataToRemoteStorage(bookMetadata: BookMetadata, fileUrl: String)
    suspend fun saveBookToLocalStorage(bookMetadata: BookMetadata, fileUrl: String)
}
