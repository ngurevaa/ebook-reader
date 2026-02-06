package ru.gureva.ebookreader.feature.bookupload.usecase

import ru.gureva.ebookreader.feature.bookupload.model.UploadBookRequest

interface UploadBookUseCase {
    suspend operator fun invoke(uploadBookRequest: UploadBookRequest)
}
