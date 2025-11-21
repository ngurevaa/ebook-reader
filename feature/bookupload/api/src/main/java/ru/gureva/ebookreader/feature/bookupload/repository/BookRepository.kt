package ru.gureva.ebookreader.feature.bookupload.repository

import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata

interface BookRepository {
    suspend fun uploadBook(bookMetadata: BookMetadata)
}
