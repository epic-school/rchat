package ru.rt.rchat.features.allusers.domain.remote

import ru.rt.rchat.features.core.data.User

interface AllUsersRepository {
    suspend fun loadUsers(): Result<List<User?>>

    suspend fun banUser(userId: Int): Result<User>
}