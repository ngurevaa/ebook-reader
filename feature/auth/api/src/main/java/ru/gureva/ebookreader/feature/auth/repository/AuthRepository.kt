package ru.gureva.ebookreader.feature.auth.repository

interface AuthRepository {
    suspend fun signUp(email: String, password: String)
}
