package ru.rt.rchat.features.allusers.domain.usecase

import ru.rt.rchat.features.allusers.domain.remote.AllUsersRepository
import ru.rt.rchat.features.core.data.User

class LoadOnlineUsersUseCase(private val allUsersRepository: AllUsersRepository) {

    suspend fun invoke(): Result<List<User>> {
        return allUsersRepository
            .loadUsers()
            .mapCatching { userList ->
                userList
                    .mapNotNull { it }
                    .filter { user -> user.info.online }
            }
    }
}