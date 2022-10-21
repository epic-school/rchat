package ru.rt.rchat.features.core.presentation

import ru.rt.rchat.features.core.data.Chat
import ru.rt.rchat.features.core.data.UserInfo

data class ChatWithUserInfo(
    val chat: Chat,
    val userInfo: UserInfo
)