package ru.gureva.ebookreader.feature.bookupload.usecase

import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import ru.gureva.ebookreader.feature.bookupload.model.UploadBookRequest
import ru.gureva.ebookreader.feature.bookupload.repository.BookRepository

class UploadBookUseCaseImpl(
    private val bookRepository: BookRepository
) : UploadBookUseCase {
    override suspend operator fun invoke(uploadBookRequest: UploadBookRequest) {
        val userId = uploadBookRequest.userId
        val fileUrl = bookRepository.uploadBookToRemoteStorage(userId, uploadBookRequest.filePath)
        val bookMetadata = BookMetadata(
            title = uploadBookRequest.title,
            author = uploadBookRequest.author,
            fileUrl = fileUrl
        )
        bookRepository.saveBookMetadataToRemoteStorage(userId, bookMetadata)
        bookRepository.saveBookToLocalStorage(uploadBookRequest.filePath, bookMetadata)
    }
}
