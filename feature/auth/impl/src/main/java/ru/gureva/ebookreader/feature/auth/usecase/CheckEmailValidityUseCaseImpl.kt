package ru.gureva.ebookreader.feature.auth.usecase

import ru.gureva.ebookreader.core.util.FieldValidator

class CheckEmailValidityUseCaseImpl(
    private val fieldValidator: FieldValidator
) : CheckEmailValidityUseCase {
    override fun invoke(email: String): Boolean {
        return fieldValidator.isValidEmail(email)
    }
}
