package ru.rt.rchat.features.core.data

import com.google.firebase.database.PropertyName

data class UserInfo(
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String = "",

    @get:PropertyName("")
    @set:PropertyName("")
    var displayName: String = "",

    @get:PropertyName("")
    @set:PropertyName("")
    var status: String = "No status",

    @get:PropertyName("")
    @set:PropertyName("")
    var profileImageUrl: String = "",

    @get:PropertyName("")
    @set:PropertyName("")
    var online: Boolean = false
)
