package ru.rt.rchat.features.allusers.domain.usecase

import ru.rt.rchat.features.allusers.domain.remote.AllUsersRepository
import ru.rt.rchat.features.autorization.data.remote.AuthRepository
import ru.rt.rchat.features.core.data.User

class BanUserUseCase(
    private val allUsersRepository: AllUsersRepository,
    private val authRepository: AuthRepository,
) {

    suspend fun invoke(userId: Int): Result<User> {
        return authRepository
            .isAdmin()
            .mapCatching { isAdmin ->
                if (!isAdmin) {
                    return Result.failure(
                        UserNotHavePermissionException
                    )
                } else {
                    return allUsersRepository.banUser(userId)
                }
            }
    }
}

object UserNotHavePermissionException :
    RuntimeException("У пользователя недостаточно прав для выполнения действия")

data class MyException(override val message: String) : RuntimeException(message)