package ru.gureva.ebookreader.feature.bookupload.usecase

import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata

interface UploadBookUseCase {
    suspend operator fun invoke(bookMetadata: BookMetadata)
}
