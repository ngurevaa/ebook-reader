package ru.gureva.ebookreader.feature.profile.usecase

import ru.gureva.ebookreader.feature.profile.model.Photo
import ru.gureva.ebookreader.feature.profile.repository.ProfileRepository

class UploadPhotoUseCaseImpl(
    private val profileRepository: ProfileRepository
) : UploadPhotoUseCase {
    override suspend operator fun invoke(photo: Photo): String {
        return profileRepository.uploadPhoto(photo)
    }
}
