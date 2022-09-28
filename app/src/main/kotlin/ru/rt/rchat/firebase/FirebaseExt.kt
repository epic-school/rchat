package ru.rt.rchat.firebase

import com.google.firebase.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

//TODO использовать вместо FirebaseReferenceValueObserver
@ExperimentalCoroutinesApi
inline fun <reified T> DatabaseReference.listen(): Flow<Result<T?>> =
    callbackFlow {
        val valueListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                close(databaseError.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val value = dataSnapshot.getValue(T::class.java)
                    trySend(success(value)).isSuccess
                } catch (exp: Exception) {
                    //Timber.e(exp)
                    if (!isClosedForSend) trySend(failure(exp)).isSuccess
                }
            }
        }
        addValueEventListener(valueListener)

        awaitClose { removeEventListener(valueListener) }
    }

//TODO использовать вместо FirebaseReferenceValueObserver
@ExperimentalCoroutinesApi
inline fun <reified T> DatabaseReference.listenChild(resultClassName: Class<T>): Flow<Result<T?>> =
    callbackFlow {
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                try {
                    val value = wrapSnapshotToClass(resultClassName, snapshot)
                    trySend(success(value)).isSuccess
                } catch (exp: Exception) {
                    if (!isClosedForSend) trySend(failure(exp)).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}
        }

        addChildEventListener(childEventListener)

        awaitClose { removeEventListener(childEventListener) }
    }
