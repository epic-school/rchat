package ru.rt.rchat.features.allusers.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.rt.rchat.features.allusers.domain.remote.AllUsersRepository
import ru.rt.rchat.features.core.data.User
import ru.rt.rchat.firebase.FirebaseDataSource

class AllUsersRepositoryImpl(
    private val dataSource: FirebaseDataSource,
    private val ioDispatcher: CoroutineDispatcher
) :
    AllUsersRepository {
    override suspend fun loadUsers(): Result<List<User?>> {
        return withContext(ioDispatcher) { dataSource.loadUsersTask() }.mapCatching {
            it.children.map { child ->
                child.getValue(
                    User::class.java
                )
            }
        }
    }

    override suspend fun banUser(userId: Int): Result<User> {
        TODO()
    }
}