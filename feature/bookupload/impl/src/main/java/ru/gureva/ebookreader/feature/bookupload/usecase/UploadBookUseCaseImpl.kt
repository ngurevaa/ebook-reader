package ru.gureva.ebookreader.feature.bookupload.usecase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ru.gureva.ebookreader.feature.bookupload.exception.NotAuthenticatedException
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import ru.gureva.ebookreader.feature.bookupload.repository.BookRepository

class UploadBookUseCaseImpl(
    private val bookRepository: BookRepository
) : UploadBookUseCase {
    override suspend operator fun invoke(bookMetadata: BookMetadata) {
        bookRepository.uploadBook(bookMetadata)
    }
}
