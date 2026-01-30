package ru.gureva.ebookreader.feature.bookupload.usecase

import android.util.Log
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import ru.gureva.ebookreader.feature.bookupload.repository.BookRepository

class UploadBookUseCaseImpl(
    private val bookRepository: BookRepository
) : UploadBookUseCase {
    override suspend operator fun invoke(bookMetadata: BookMetadata) {
        val fileUrl = bookRepository.uploadBookToRemoteStorage(bookMetadata)
        bookRepository.saveBookMetadataToRemoteStorage(bookMetadata, fileUrl)
        bookRepository.saveBookToLocalStorage(bookMetadata, fileUrl)
    }
}
