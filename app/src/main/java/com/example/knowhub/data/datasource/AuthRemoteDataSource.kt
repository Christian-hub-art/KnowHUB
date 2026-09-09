package com.example.knowhub.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth){

    val currentUser: FirebaseUser? = auth.currentUser

    suspend fun signIn(correoElectronico: String, contrasena: String) {
        auth.signInWithEmailAndPassword(correoElectronico, contrasena).await()
    }

    suspend fun signUp(correoElectronico: String, contrasena: String) {
        auth.createUserWithEmailAndPassword(correoElectronico, contrasena).await()
    }

    fun signOut() {
        auth.signOut()
    }


}