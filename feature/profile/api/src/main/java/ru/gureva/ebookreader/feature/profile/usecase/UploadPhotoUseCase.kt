package ru.gureva.ebookreader.feature.profile.usecase

import ru.gureva.ebookreader.feature.profile.model.Photo

interface UploadPhotoUseCase {
    suspend operator fun invoke(photo: Photo): String
}
