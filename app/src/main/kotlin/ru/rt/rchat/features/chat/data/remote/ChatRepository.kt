package ru.rt.rchat.features.chat.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.rt.rchat.features.core.data.Message
import ru.rt.rchat.firebase.FirebaseDataSource

class ChatRepository(private val dataSource: FirebaseDataSource, private val ioDispatcher: CoroutineDispatcher) {

    suspend fun loadChat(chatId: String) = withContext(ioDispatcher) { dataSource.loadChatTask(chatId) }

    suspend fun newMessage(cahtId: String, message: Message) =
        withContext(ioDispatcher) { dataSource.pushNewMessage(cahtId, message) }

    suspend fun updateLastMessage(chatId: String, message: Message) =
        withContext(ioDispatcher) { dataSource.updateLastMessage(chatId, message) }

    suspend fun removeMessage(messageId: String) = withContext(ioDispatcher) { dataSource.removeMessages(messageId) }
}