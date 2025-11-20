package ru.gureva.ebookreader.feature.auth.usecase

interface SignUpUseCase {
    suspend operator fun invoke(email: String, password: String)
}
