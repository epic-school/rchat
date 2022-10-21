package ru.rt.rchat.features.allusers.presentation

import ru.rt.rchat.features.core.data.User

class UsersContract {
    sealed class State {
        object Loading : State()
        data class Error(val errorModel: ErrorModel) : State()
        data class Content(val users: List<User> = emptyList()) : State()
    }

    sealed class Effect {
        data class DialogMessage(val message: String) : Effect()
        data class DialogError(val error: ErrorModel) : Effect()
    }

    sealed class Event {
        object OnViewReady: Event()
        object OnUserClick : Event()
        object OnBanUserClick : Event()
    }

    data class ErrorModel(val message: String)
}