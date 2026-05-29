package com.example.socialconnect.DI

import android.content.Context
import com.example.socialconnect.Data.Repository.AuthRepositoryImpl
import com.example.socialconnect.Data.Repository.EditProfileRepositoryImpl
import com.example.socialconnect.Domain.Repository.AuthRepository
import com.example.socialconnect.Domain.Repository.EditProfileRepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


import com.example.socialconnect.R

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
}