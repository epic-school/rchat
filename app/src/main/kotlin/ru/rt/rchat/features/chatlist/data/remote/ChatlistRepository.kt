package ru.rt.rchat.features.chatlist.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.rt.rchat.firebase.FirebaseDataSource

class ChatListRepository(private val dataSource: FirebaseDataSource, private val ioDispatcher: CoroutineDispatcher) {

    suspend fun loadFriends(userId: String) = withContext(ioDispatcher) { dataSource.loadFriendsTask(userId) }

    suspend fun loadInfo(userFriendId: String) = withContext(ioDispatcher) { dataSource.loadUserInfoTask(userFriendId) }

    suspend fun removeChat(chatId: String) = withContext(ioDispatcher) { dataSource.removeChat(chatId) }
}