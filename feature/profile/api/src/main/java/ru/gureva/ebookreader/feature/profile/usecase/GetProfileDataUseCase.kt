package ru.gureva.ebookreader.feature.profile.usecase

import ru.gureva.ebookreader.feature.profile.model.ProfileData

interface GetProfileDataUseCase {
    suspend operator fun invoke(): ProfileData
}
