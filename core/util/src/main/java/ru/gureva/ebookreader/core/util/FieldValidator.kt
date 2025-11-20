package ru.gureva.ebookreader.core.util

class FieldValidator {
    fun isValidEmail(email: String): Boolean {
        return Regex(EMAIL_REGEX).matches(email)
    }

    fun isValidPassword(password: String): Boolean {
        return Regex(PASSWORD_REGEX).matches(password)
    }

    companion object {
        private const val EMAIL_REGEX = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
                "*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
        private const val PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
    }
}
