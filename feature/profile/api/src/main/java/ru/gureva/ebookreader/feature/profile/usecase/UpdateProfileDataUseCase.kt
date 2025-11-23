package ru.gureva.ebookreader.feature.profile.usecase

import ru.gureva.ebookreader.feature.profile.model.ProfileData

interface UpdateProfileDataUseCase {
    suspend operator fun invoke(profileData: ProfileData)
}
