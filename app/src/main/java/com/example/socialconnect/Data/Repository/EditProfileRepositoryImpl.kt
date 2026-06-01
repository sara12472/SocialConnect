package com.example.socialconnect.Data.Repository

import android.content.Context
import android.net.Uri
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Data.Model.User
import com.example.socialconnect.Domain.Repository.EditProfileRepository
import com.example.socialconnect.Data.Remote.RetrofitInstance
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class EditProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore) :
    EditProfileRepository {

    override suspend fun uploadMediaToCloudinary(
        uri: Uri,
        context: Context,
        type: String
    ): String {

        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream!!.readBytes()

        val mimeType = context.contentResolver.getType(uri) ?: "image/*"

        val requestFile = bytes.toRequestBody(mimeType.toMediaTypeOrNull())

        val filePart = MultipartBody.Part.createFormData(
            "file",
            if (type == "video") "video.mp4" else "image.jpg",
            requestFile
        )

        val uploadPreset = "social_upload"
            .toRequestBody("text/plain".toMediaTypeOrNull())

        val resourceType = if (type == "video") "video" else "image"

        val response = RetrofitInstance.api.uploadMedia(
            resourceType = resourceType,
            file = filePart,
            uploadPreset = uploadPreset
        )

        return response.secure_url
    }
    override suspend fun updateUser(user: User): Resource<Boolean> {
        return try {
            firestore.collection("users")
                .document(user.uid)
                .set(user)
                .await()
            updatePostsForUser(user)

            Resource.Success(true)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Update failed")
        }
    }
    override suspend fun getUser(uid: String): User {

        val document = firestore
            .collection("users")
            .document(uid)
            .get()
            .await()

        return document.toObject(User::class.java) ?: User()
    }
    private suspend fun updatePostsForUser(user: User) {

        val snapshot = firestore.collection("posts")
            .whereEqualTo("userId", user.uid)
            .get()
            .await()

        snapshot.documents.forEach { doc ->

            doc.reference.update(
                mapOf(
                    "userName" to user.name,
                    "userProfile" to user.profileImage
                )
            ).await()
        }
    }
}