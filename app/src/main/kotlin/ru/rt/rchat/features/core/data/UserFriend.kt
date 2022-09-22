package ru.rt.rchat.features.core.data

import com.google.firebase.database.PropertyName

data class UserFriend(
    @get:PropertyName("userID")
    @set:PropertyName("userID")
    var userId: String = ""
)
