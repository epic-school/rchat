package ru.rt.rchat.features.core.data

data class CreateUser(
    val displayName: String,
    val email: String,
    val password: String
)
