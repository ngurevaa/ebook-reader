package ru.gureva.ebookreader.feature.profile.usecase

import ru.gureva.ebookreader.feature.profile.repository.ProfileRepository

class LogoutUseCaseImpl(
    private val profileRepository: ProfileRepository
) : LogoutUseCase {
    override suspend operator fun invoke() {
        profileRepository.logout()
    }
}
