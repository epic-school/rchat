package ru.rt.rchat.features.allusers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ru.rt.rchat.features.allusers.domain.usecase.BanUserUseCase
import ru.rt.rchat.features.allusers.domain.usecase.LoadOnlineUsersUseCase
import java.io.IOException
import java.net.SocketTimeoutException

class UserViewModel(
    private val loadOnlineUsersUseCase: LoadOnlineUsersUseCase,
    private val banUserUseCase: BanUserUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun showUsersOnline() {
        viewModelScope.launch(ioDispatcher) {
            loadOnlineUsersUseCase
                .invoke()
                .onSuccess {
                    // showOnScreen()
                }.onFailure { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            // showError("Плохое соединение")
                        }
                        is IOException -> {
                            // showError("Проверьте подключение к сети")
                        }
                        else -> {
                            // showError("Неизвестная ошибка")
                        }
                    }
                }
        }
    }


    fun banUser() {
        viewModelScope.launch(ioDispatcher) {
            banUserUseCase
                .invoke(123)
                .onSuccess { }
                .onFailure { error -> showError(error.message) }

        }
    }

    private fun showError(message: String?) {

    }
}