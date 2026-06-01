package com.example.socialconnect.Data.Remote

import com.example.socialconnect.Data.Model.CloudinaryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface CloudinaryApi {

    @Multipart
    @POST("v1_1/do8kayjuw/{resource_type}/upload")
    suspend fun uploadMedia(
        @Path("resource_type") resourceType: String,
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: RequestBody
    ): CloudinaryResponse
}