package ru.rt.rchat.features.autorization.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.rt.rchat.features.core.data.CreateUser
import ru.rt.rchat.features.core.data.Login
import ru.rt.rchat.firebase.FirebaseAuthSource

class AuthRepository(
    private val authSource: FirebaseAuthSource,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun login(
        email: String,
        pass: String
    ) = withContext(ioDispatcher) { authSource.loginWithEmailAndPassword(Login(email, pass)) }

    suspend fun createUser(
        displayName: String,
        email: String,
        password: String
    ) = withContext(ioDispatcher) { authSource.createUser(CreateUser(displayName, email, password)) }

    suspend fun isAdmin(): Result<Boolean> {
        withContext(ioDispatcher) { TODO() }
    }
}