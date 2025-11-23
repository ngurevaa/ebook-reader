package ru.gureva.ebookreader.feature.profile.usecase

import android.util.Log
import ru.gureva.ebookreader.feature.profile.model.ProfileData
import ru.gureva.ebookreader.feature.profile.repository.ProfileRepository

class GetProfileDataUseCaseImpl(
    private val profileRepository: ProfileRepository
) : GetProfileDataUseCase {
    override suspend operator fun invoke(): ProfileData {
        return profileRepository.getData()
    }
}
