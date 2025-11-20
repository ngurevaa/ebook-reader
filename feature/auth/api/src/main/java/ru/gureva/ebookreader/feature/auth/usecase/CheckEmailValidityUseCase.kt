package ru.gureva.ebookreader.feature.auth.usecase

interface CheckEmailValidityUseCase {
    operator fun invoke(email: String): Boolean
}
