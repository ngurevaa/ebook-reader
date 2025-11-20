package ru.gureva.ebookreader.feature.auth.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gureva.ebookreader.feature.auth.datasource.RemoteFirebaseDatasource

class AuthRepositoryImpl(
    private val remoteFirebaseDatasource: RemoteFirebaseDatasource
) : AuthRepository {
    override suspend fun signUp(email: String, password: String) {
        withContext(Dispatchers.IO) {
            remoteFirebaseDatasource.createUserWithEmailAndPassword(email, password)
        }
    }

    override suspend fun signIn(email: String, password: String) {
        withContext(Dispatchers.IO) {
            remoteFirebaseDatasource.signInWithEmailAndPassword(email, password)
        }
    }
}
