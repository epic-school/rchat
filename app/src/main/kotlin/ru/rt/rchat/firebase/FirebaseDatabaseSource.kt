package ru.rt.rchat.firebase


import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await
import ru.rt.rchat.features.core.data.*
import kotlin.Result.Companion.success

class FirebaseReferenceConnectedObserver(private val dbInstance: FirebaseDatabase) {

    private var valueEventListener: ValueEventListener? = null
    private var dbRef: DatabaseReference? = null
    private var userRef: DatabaseReference? = null

    fun start(userId: String) {
        this.userRef = dbInstance.reference.child("users/$userId/info/online")
        this.valueEventListener = getEventListener(userId)
        this.dbRef = dbInstance.getReference(".info/connected")
            .apply { addValueEventListener(valueEventListener!!) }
    }

    private fun getEventListener(userId: String): ValueEventListener {
        return (object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                if (connected) {
                    dbInstance.reference.child("users/$userId/info/online").setValue(true)
                    userRef?.onDisconnect()?.setValue(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun clear() {
        valueEventListener?.let { dbRef?.removeEventListener(it) }
        userRef?.setValue(false)
        valueEventListener = null
        dbRef = null
        userRef = null
    }
}

// Task based
class FirebaseDataSource(private val dbInstance: FirebaseDatabase) {
    //region Private

    private fun refToPath(path: String): DatabaseReference {
        return dbInstance.reference.child(path)
    }

    //region Update

    suspend fun updateUserProfileImageUrl(userId: String, url: String): Result<Unit> =
        safeVoidCall { refToPath("users/$userId/info/profileImageUrl").setValue(url).await() }

    suspend fun updateUserStatus(userId: String, status: String): Result<Unit> =
        safeVoidCall { refToPath("users/$userId/info/status").setValue(status).await() }

    suspend fun updateLastMessage(chatId: String, message: Message): Result<Unit> =
        safeVoidCall { refToPath("chats/$chatId/lastMessage").setValue(message).await() }

    suspend fun updateNewFriend(myUser: UserFriend, otherUser: UserFriend): Result<Unit> =
        safeVoidCall {
            refToPath("users/${myUser.userId}/friends/${otherUser.userId}").setValue(otherUser).await()
            refToPath("users/${otherUser.userId}/friends/${myUser.userId}").setValue(myUser).await()
        }

    suspend fun updateNewSentRequest(userId: String, userRequest: UserRequest): Result<Unit> =
        safeVoidCall { refToPath("users/${userId}/sentRequests/${userRequest.userId}").setValue(userRequest).await() }

    suspend fun updateNewNotification(otheruserId: String, userNotification: UserNotification): Result<Unit> =
        safeVoidCall {
            refToPath("users/${otheruserId}/notifications/${userNotification.userId}")
                .setValue(userNotification)
                .await()
        }

    suspend fun updateNewUser(user: User): Result<Unit> =
        safeVoidCall { refToPath("users/${user.info.id}").setValue(user).await() }

    suspend fun updateNewChat(chat: Chat): Result<Unit> =
        safeVoidCall { refToPath("chats/${chat.info.id}").setValue(chat).await() }

    suspend fun pushNewMessage(messagesId: String, message: Message): Result<Unit> =
        safeVoidCall { refToPath("messages/$messagesId").push().setValue(message).await() }

    //endregion

    //region Remove

    suspend fun removeNotification(userId: String, notificationId: String): Result<Unit> =
        safeVoidCall { refToPath("users/${userId}/notifications/$notificationId").setValue(null).await() }

    suspend fun removeFriend(userId: String, friendId: String): Result<Unit> =
        safeVoidCall {
            refToPath("users/${userId}/friends/$friendId").setValue(null).await()
            refToPath("users/${friendId}/friends/$userId").setValue(null).await()
        }

    suspend fun removeSentRequest(userId: String, sentRequestId: String): Result<Unit> =
        safeVoidCall { refToPath("users/${userId}/sentRequests/$sentRequestId").setValue(null).await() }

    suspend fun removeChat(chatId: String): Result<Unit> =
        safeVoidCall { refToPath("chats/$chatId").setValue(null).await() }

    suspend fun removeMessages(messagesId: String): Result<Unit> =
        safeVoidCall { refToPath("messages/$messagesId").setValue(null).await() }
    //endregion

    //region Load
    private fun attachValueListenerToTaskCompletion(src: TaskCompletionSource<DataSnapshot>): ValueEventListener {
        return (object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                src.setException(Exception(error.message))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                src.setResult(snapshot)
            }
        })
    }

    suspend fun loadUserTask(userId: String): Result<DataSnapshot> = safeCall {
        val src = TaskCompletionSource<DataSnapshot>()
        val listener = attachValueListenerToTaskCompletion(src)
        refToPath("users/$userId").addListenerForSingleValueEvent(listener)
        return success(src.task.await())
    }

    suspend fun loadUserInfoTask(userId: String): Result<DataSnapshot> = safeCall {
        val src = TaskCompletionSource<DataSnapshot>()
        val listener = attachValueListenerToTaskCompletion(src)
        refToPath("users/$userId/info").addListenerForSingleValueEvent(listener)
        return success(src.task.await())
    }

    suspend fun loadUsersTask(): Result<DataSnapshot> = safeCall {
        val src = TaskCompletionSource<DataSnapshot>()
        val listener = attachValueListenerToTaskCompletion(src)
        refToPath("users").addListenerForSingleValueEvent(listener)
        return success(src.task.await())
    }

    suspend fun loadFriendsTask(userId: String): Result<DataSnapshot> = safeCall {
        val src = TaskCompletionSource<DataSnapshot>()
        val listener = attachValueListenerToTaskCompletion(src)
        refToPath("users/$userId/friends").addListenerForSingleValueEvent(listener)
        return success(src.task.await())
    }

    suspend fun loadChatTask(chatId: String): Result<DataSnapshot> = safeCall {
        val src = TaskCompletionSource<DataSnapshot>()
        val listener = attachValueListenerToTaskCompletion(src)
        refToPath("chats/$chatId").addListenerForSingleValueEvent(listener)
        return success(src.task.await())
    }

    suspend fun loadNotificationsTask(userId: String): Result<DataSnapshot> = safeCall {
        val src = TaskCompletionSource<DataSnapshot>()
        val listener = attachValueListenerToTaskCompletion(src)
        refToPath("users/$userId/notifications").addListenerForSingleValueEvent(listener)
        return success(src.task.await())
    }

    //endregion
}
