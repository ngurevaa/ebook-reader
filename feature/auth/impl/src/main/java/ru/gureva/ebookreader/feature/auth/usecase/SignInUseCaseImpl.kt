package ru.gureva.ebookreader.feature.auth.usecase

import ru.gureva.ebookreader.feature.auth.repository.AuthRepository

class SignInUseCaseImpl(
    private val authRepository: AuthRepository
) : SignInUseCase {
    override suspend operator fun invoke(email: String, password: String) {
        authRepository.signIn(email, password)
    }
}
