package ru.gureva.ebookreader.feature.auth.usecase

import ru.gureva.ebookreader.core.util.FieldValidator

class CheckPasswordValidityUseCaseImpl(
    private val fieldValidator: FieldValidator
) : CheckPasswordValidityUseCase {
    override fun invoke(password: String): Boolean {
        return fieldValidator.isValidPassword(password)
    }
}
