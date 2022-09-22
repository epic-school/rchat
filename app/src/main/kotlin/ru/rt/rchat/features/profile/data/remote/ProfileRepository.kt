package ru.rt.rchat.features.profile.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import ru.rt.rchat.firebase.FirebaseAuthSource
import ru.rt.rchat.firebase.FirebaseDataSource
import kotlin.coroutines.CoroutineContext

class ProfileRepository(
    private val dataSource: FirebaseDataSource,
    private val authSource: FirebaseAuthSource,
    private val ioDispatcher: CoroutineDispatcher
) : CoroutineScope {
    private val parentJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = parentJob + ioDispatcher

    suspend fun loadProfile(userId: String) = withContext(coroutineContext) { dataSource.loadUserTask(userId) }

    suspend fun updateUserProfileImageUrl(userId: String, url: String) =
        withContext(coroutineContext) { dataSource.updateUserProfileImageUrl(userId, url) }

    suspend fun updateUserStatus(userId: String, status: String) =
        withContext(coroutineContext) { dataSource.updateUserStatus(userId, status) }

    suspend fun logout() = withContext(coroutineContext) { authSource.logout() } // TODO: clean local storage and cache
}