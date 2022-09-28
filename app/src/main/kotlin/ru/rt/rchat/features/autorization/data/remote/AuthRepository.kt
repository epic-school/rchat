package ru.rt.rchat.features.autorization.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.rt.rchat.features.core.data.CreateUser
import ru.rt.rchat.features.core.data.Login
import ru.rt.rchat.firebase.FirebaseAuthSource

class AuthRepository(private val authSource: FirebaseAuthSource, private val ioDispatcher: CoroutineDispatcher) {

    suspend fun login(login: Login) = withContext(ioDispatcher) { authSource.loginWithEmailAndPassword(login) }

    suspend fun createUser(newUser: CreateUser) = withContext(ioDispatcher) { authSource.createUser(newUser) }
}