package com.example.knowhub.data.datasource

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {
    val currentUser: FirebaseUser? get() = auth.currentUser

    suspend fun signIn(correoElectronico: String, contrasena: String) {
        auth.signInWithEmailAndPassword(correoElectronico, contrasena).await()
    }

    suspend fun signUp(correoElectronico: String, contrasena: String) {
        auth.createUserWithEmailAndPassword(correoElectronico, contrasena).await()
    }

    fun signOut() {
        auth.signOut()
    }

    suspend fun updateProfileImage(photoUrl: String) {
        val uri = Uri.parse(photoUrl)
        currentUser?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
        )?.await()
    }
}
