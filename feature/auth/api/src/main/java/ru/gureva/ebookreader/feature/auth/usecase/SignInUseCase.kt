package ru.gureva.ebookreader.feature.auth.usecase

interface SignInUseCase {
    suspend operator fun invoke(email: String, password: String)
}
