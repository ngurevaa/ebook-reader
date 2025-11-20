package ru.gureva.ebookreader.feature.auth.usecase

interface CheckPasswordValidityUseCase {
    operator fun invoke(password: String): Boolean
}
