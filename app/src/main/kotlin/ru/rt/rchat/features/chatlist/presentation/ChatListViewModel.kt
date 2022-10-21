package ru.rt.rchat.features.chatlist.presentation

import androidx.lifecycle.ViewModel
import ru.rt.rchat.features.core.data.UserFriend
import ru.rt.rchat.features.core.data.UserInfo
import ru.rt.rchat.features.core.presentation.ChatWithUserInfo

class ChatListViewModel : ViewModel() {

    private val myId: String = ""

//    fun setupChats() {
//        loadFriends().map { loadUserInfo(it.userId) }.map { loadChat(it) }
//    }

//    fun loadFriends(): List<UserFriend> {
//        return repository.loadFriends(myId)
//    }

//    fun loadUserInfo(userId: String): UserInfo {
//        return repository.loadUserInfo(userId)
//    }

    fun loadChat(userInfo: UserInfo) {
//        repository.loadChat(getChatId(myId, userInfo.id))
    }

    private fun getChatId(myId: String, userId: String) = if (myId > userId) myId + userId else userId + myId
}