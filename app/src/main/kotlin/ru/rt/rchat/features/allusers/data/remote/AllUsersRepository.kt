package ru.rt.rchat.features.allusers.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.rt.rchat.firebase.FirebaseDataSource

class AllUsersRepository(private val dataSource: FirebaseDataSource, private val ioDispatcher: CoroutineDispatcher) {
    suspend fun loadUsers() = withContext(ioDispatcher) { dataSource.loadUsersTask() }
}