package ru.gureva.ebookreader.feature.auth.usecase

import ru.gureva.ebookreader.feature.auth.repository.AuthRepository

class SignUpUseCaseImpl(
    private val authRepository: AuthRepository
) : SignUpUseCase {
    override suspend operator fun invoke(email: String, password: String) {
        authRepository.signUp(email, password)
    }
}
