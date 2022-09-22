package ru.rt.rchat.firebase

import com.google.firebase.database.DataSnapshot

fun <T> wrapSnapshotToClass(className: Class<T>, snap: DataSnapshot): T? {
    return snap.getValue(className)
}

fun <T> wrapSnapshotToArrayList(className: Class<T>, snap: DataSnapshot): MutableList<T> {
    val arrayList: MutableList<T> = arrayListOf()
    for (child in snap.children) {
        child.getValue(className)?.let { arrayList.add(it) }
    }
    return arrayList
}

// Always returns the same combined id when comparing the two users id's
fun convertTwoUserIDs(userID1: String, userID2: String): String {
    return if (userID1 < userID2) {
        userID2 + userID1
    } else {
        userID1 + userID2
    }
}

inline fun <T> safeCall(action: () -> Result<T>): Result<T> {
    return try {
        action.invoke()
    } catch (e: Exception) {
        Result.failure(e)
    }
}

inline fun safeVoidCall(action: () -> Void): Result<Unit> {
    return try {
        action.invoke()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}