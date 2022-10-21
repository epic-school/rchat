package ru.rt.rchat.features.allusers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.rt.rchat.features.allusers.domain.usecase.BanUserUseCase
import ru.rt.rchat.features.allusers.domain.usecase.LoadOnlineUsersUseCase
import ru.rt.rchat.features.allusers.presentation.UsersContract.*
import ru.rt.rchat.features.core.data.User
import java.io.IOException
import java.net.SocketTimeoutException

class UserViewModel(
    private val loadOnlineUsersUseCase: LoadOnlineUsersUseCase,
    private val banUserUseCase: BanUserUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    init {
        subscribeEvents()
    }

    private fun showUsersOnline() {
        viewModelScope.launch(ioDispatcher) {
            sendState(State.Loading)
            loadOnlineUsersUseCase
                .invoke()
                .onSuccess {
                    sendState(State.Content(users = it))
                }.onFailure { error ->
                    val errorMessage = when (error) {
                        is SocketTimeoutException -> {
                            "Плохое соединение"
                        }
                        is IOException -> {
                            "Проверьте подключение к сети"
                        }
                        else -> {
                            "Неизвестная ошибка"
                        }
                    }
                    sendState(State.Error(ErrorModel(message = errorMessage)))
                }
        }
    }


    private fun banUser() {
        viewModelScope.launch(ioDispatcher) {
            banUserUseCase
                .invoke(123)
                .onSuccess {
                    sendEffect(Effect.DialogMessage(message = "User was blocked"))
                }
                .onFailure { error ->
                    val message = error.message ?: "Something wrong"
                    sendEffect(Effect.DialogError(error = ErrorModel(message)))
                }

        }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.OnViewReady -> showUsersOnline()
            is Event.OnUserClick -> { /* TODO: */  }
            is Event.OnBanUserClick -> banUser()
        }
    }

    private fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    private fun sendState(state: State) {
        viewModelScope.launch { _uiState.emit(state) }
    }

    fun setEvent(event : Event) {
        viewModelScope.launch { _event.emit(event) }
    }
}

