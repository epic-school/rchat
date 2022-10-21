package ru.rt.rchat.features.autorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.rt.rchat.features.autorization.data.remote.AuthRepository
import ru.rt.rchat.features.autorization.presentation.AuthorizationContract.*

class AuthorizationViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.Initial)
    val uiState = _uiState.asStateFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    init {
        subscribeEvents()
    }

    private fun createUser(
        displayName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            sendState(State.Loading)
            authRepository.createUser(displayName, email, password)
                .onSuccess {
                    navigateToAllUsers()
                }
                .onFailure {
                    sendState(State.Error(ErrorModel(message = it.message ?: "Can not create user")))
                }
        }
    }

    private fun loginUser(email: String, pass: String) {
        viewModelScope.launch {
            sendState(State.Loading)
            authRepository.login(email, pass)
                .onSuccess {
                    navigateToAllUsers()
                }
                .onFailure {
                    sendState(State.Error(ErrorModel(message = it.message ?: "Can not login")))
                }
        }
    }

    private fun navigateToAllUsers() {
        sendEffect(Effect.NavigateToAllUsers)
    }

    private fun navigateToCreateUser() {
        sendEffect(Effect.NavigateToCreateUser)
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    private fun sendState(state: State) {
        viewModelScope.launch { _uiState.emit(state) }
    }

    private fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.OnLoginClick -> {
                loginUser(event.email, event.pass)
            }
            is Event.OnCreateUserClick -> {
                createUser(event.displayName, event.email, event.pass)
            }
            is Event.OnRegistrationClick -> {
                navigateToCreateUser()
            }
        }
    }
}