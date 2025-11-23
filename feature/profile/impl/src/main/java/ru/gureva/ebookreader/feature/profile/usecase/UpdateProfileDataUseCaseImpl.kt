package ru.gureva.ebookreader.feature.profile.usecase

import ru.gureva.ebookreader.feature.profile.model.ProfileData
import ru.gureva.ebookreader.feature.profile.repository.ProfileRepository

class UpdateProfileDataUseCaseImpl(
    private val profileRepository: ProfileRepository
) : UpdateProfileDataUseCase {
    override suspend operator fun invoke(profileData: ProfileData) {
        profileRepository.updateData(profileData)
    }
}
