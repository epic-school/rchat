package ru.rt.rchat.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ru.rt.rchat.features.core.data.CreateUser
import ru.rt.rchat.features.core.data.Login
import kotlin.Result.Companion.success


class FirebaseAuthSource(private val auth: FirebaseAuth) {

    //TODO не забыть выставить нужный диспетчер снаружи для suspend
    suspend fun loginWithEmailAndPassword(login: Login): Result<AuthResult> =
        safeCall {
            success(auth.signInWithEmailAndPassword(login.email, login.password).await())
        }

    //TODO не забыть выставить нужный диспетчер снаружи для suspend
    suspend fun createUser(createUser: CreateUser): Result<AuthResult> =
        safeCall {
            success(auth.createUserWithEmailAndPassword(createUser.email, createUser.password).await())
        }

    fun logout() {
        auth.signOut()
    }

    //TODO использовать вместо зоопарка с FirebaseAuthStateObserver
    @ExperimentalCoroutinesApi
    fun getUserInfo(): Flow<FirebaseUser?> =
        callbackFlow {
            val authStateListener = FirebaseAuth.AuthStateListener {
                trySendBlocking(it.currentUser)
            }
            auth.addAuthStateListener(authStateListener)
            awaitClose {
                auth.removeAuthStateListener(authStateListener)
            }
        }
}