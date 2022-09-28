package ru.rt.rchat.features.core.data

import com.google.firebase.database.PropertyName

data class UserRequest(
    @get:PropertyName("")
    @set:PropertyName("")
    var userId: String = ""
)