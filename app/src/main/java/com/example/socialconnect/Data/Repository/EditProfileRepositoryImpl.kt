package com.example.socialconnect.Data.Repository

import android.content.Context
import android.net.Uri
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Data.Model.User
import com.example.socialconnect.Domain.Repository.EditProfileRepository
import com.example.socialconnect.Domain.RetrofitInstance
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class EditProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore) :
    EditProfileRepository {

    override suspend fun uploadImageToCloudinary(
        uri: Uri,
        context: Context
    ): String {

        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream!!.readBytes()

        val requestFile = bytes.toRequestBody("image/*".toMediaTypeOrNull())

        val filePart = MultipartBody.Part.createFormData(
            "file",
            "image.jpg",
            requestFile
        )

        val uploadPreset = "profileImage"
            .toRequestBody("text/plain".toMediaTypeOrNull())

        val response = RetrofitInstance.api.uploadImage(
            filePart,
            uploadPreset
        )

        return response.secure_url
    }
    override suspend fun updateUser(user: User): Resource<Boolean> {
        return try {
            firestore.collection("users")
                .document(user.uid)
                .set(user)
                .await()

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
}