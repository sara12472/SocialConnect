package com.example.socialconnect.DI

import android.content.Context
import com.example.socialconnect.Data.Repository.AuthRepositoryImpl
import com.example.socialconnect.Data.Repository.CommentRepositoryImpl
import com.example.socialconnect.Data.Repository.EditProfileRepositoryImpl
import com.example.socialconnect.Data.Repository.FcmRepositoryImpl
import com.example.socialconnect.Data.Repository.FollowRepositoryImpl
import com.example.socialconnect.Data.Repository.MessageRepositoryImpl
import com.example.socialconnect.Data.Repository.NotificationRepositoryImpl
import com.example.socialconnect.Data.Repository.PostRepositoryImpl
import com.example.socialconnect.Data.Repository.SavedPostRepositoryImpl
import com.example.socialconnect.Domain.Repository.AuthRepository
import com.example.socialconnect.Domain.Repository.CommentRepository
import com.example.socialconnect.Domain.Repository.EditProfileRepository
import com.example.socialconnect.Domain.Repository.FcmRepository
import com.example.socialconnect.Domain.Repository.FollowRepository
import com.example.socialconnect.Domain.Repository.MessageRepository
import com.example.socialconnect.Domain.Repository.NotificationRepository
import com.example.socialconnect.Domain.Repository.PostRepository
import com.example.socialconnect.Domain.Repository.SavedPostRepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


import com.example.socialconnect.R
import dagger.Binds

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {

        return AuthRepositoryImpl(auth, firestore)
    }
    @Provides
    @Singleton
    fun provideEditProfileRepository(
        firestore: FirebaseFirestore
    ): EditProfileRepository {

        return EditProfileRepositoryImpl(firestore)
    }
    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context
    ): GoogleSignInClient {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }
    @Provides
    @Singleton
    fun providePostRepository(
        firestore: FirebaseFirestore
    ): PostRepository {

        return PostRepositoryImpl(firestore)
    }
    @Provides
    @Singleton
    fun provideFollowRepository(
        firestore: FirebaseFirestore
    ): FollowRepository {
        return FollowRepositoryImpl(firestore)
    }
    @Provides
    @Singleton
    fun provideCommentRepository(
        firestore: FirebaseFirestore
    ): CommentRepository {
        return CommentRepositoryImpl(firestore)
    }
    @Provides
    @Singleton
    fun provideNotificationRepository(
        database: FirebaseFirestore
    ): NotificationRepository {
        return NotificationRepositoryImpl(database)
    }
    @Provides
    @Singleton
    fun provideFcmRepository(): FcmRepository {
        return FcmRepositoryImpl()
    }
    @Provides
    @Singleton
    fun provideSavedPostRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): SavedPostRepository {
        return SavedPostRepositoryImpl(
            firestore,
            auth
        )
    }
    @Provides
    @Singleton
    fun provideMessageRepository(
        firestore: FirebaseFirestore
    ): MessageRepository {

        return MessageRepositoryImpl(firestore)
    }



}