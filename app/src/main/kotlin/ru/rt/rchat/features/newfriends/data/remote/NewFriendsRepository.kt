package ru.rt.rchat.features.newfriends.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.rt.rchat.features.core.data.UserFriend
import ru.rt.rchat.firebase.FirebaseDataSource

class NewFriendsRepository(private val dataSource: FirebaseDataSource, val ioDispatcher: CoroutineDispatcher) {

    suspend fun loadNewFriends(userId: String) = withContext(ioDispatcher) { dataSource.loadNotificationsTask(userId) }

    suspend fun updateNewFriend(user: UserFriend, friend: UserFriend) =
        withContext(ioDispatcher) { dataSource.updateNewFriend(user, friend) }

}