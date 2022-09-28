package ru.rt.rchat.features.core.data

import com.google.firebase.database.PropertyName

data class User(
    @get:PropertyName("")
    @set:PropertyName("")
    var info: UserInfo,

    @get:PropertyName("")
    @set:PropertyName("")
    var friends: HashMap<String, UserFriend> = HashMap(),

    @get:PropertyName("")
    @set:PropertyName("")
    var notifications: HashMap<String, UserNotification> = HashMap(),

    @get:PropertyName("")
    @set:PropertyName("")
    var sentRequests: HashMap<String, UserRequest> = HashMap()
)