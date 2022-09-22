package ru.rt.rchat.features.core.data

import com.google.firebase.database.PropertyName

data class Chat(
    @get:PropertyName("")
    @set:PropertyName("")
    var lastMessage: Message = Message(),

    @get:PropertyName("")
    @set:PropertyName("")
    var info: ChatInfo = ChatInfo()
)
