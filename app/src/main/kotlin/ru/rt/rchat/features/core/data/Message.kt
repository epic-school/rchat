package ru.rt.rchat.features.core.data

import com.google.firebase.database.PropertyName
import java.util.*

data class Message(
    @get:PropertyName("senderID")
    @set:PropertyName("senderID")
    var senderId: String = "",

    @get:PropertyName("text")
    @set:PropertyName("text")
    var text: String = "",

    @get:PropertyName("epochTimeMs")
    @set:PropertyName("epochTimeMs")
    var epochTimeMs: Long = Date().time,

    @get:PropertyName("seen")
    @set:PropertyName("seen")
    var seen: Boolean = false
)
