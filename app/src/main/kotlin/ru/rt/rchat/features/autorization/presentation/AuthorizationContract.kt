package ru.rt.rchat.features.autorization.presentation

class AuthorizationContract {
    sealed class State {
        object Initial : State()
        object Loading : State()
        data class Error(val errorModel: ErrorModel) : State()
    }

    sealed class Effect {
        object NavigateToCreateUser : Effect()
        object NavigateToAllUsers : Effect()
    }

    sealed class Event {
        data class OnLoginClick(
            val email: String,
            val pass: String,
        ) : Event()

        data class OnCreateUserClick(
            val displayName: String,
            val email: String,
            val pass: String,
        ) : Event()

        object OnRegistrationClick : Event()
    }

    data class ErrorModel(val message: String)
}