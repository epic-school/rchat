package ru.rt.rchat.firebase

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlin.Result.Companion.success

// Task based
class FirebaseStorageSource(private val storageInstance: FirebaseStorage) {
    suspend fun uploadUserImage(userID: String, bArr: ByteArray): Result<Uri> {
        val path = "user_photos/$userID/profile_image"
        val ref = storageInstance.reference.child(path)

        return safeCall { success(ref.putBytes(bArr).continueWithTask { ref.downloadUrl }.await()) }
    }
}