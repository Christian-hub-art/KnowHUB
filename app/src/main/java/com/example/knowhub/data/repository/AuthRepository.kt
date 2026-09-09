package com.example.knowhub.data.repository

import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    val currentUser: FirebaseUser? = authRemoteDataSource.currentUser

    suspend fun sigIn(correoElectronico: String, contrasena: String){
        authRemoteDataSource.signIn(correoElectronico,contrasena)
    }
    suspend fun signUp(correoElectronico: String, contrasena: String){
        authRemoteDataSource.signUp(correoElectronico,contrasena)
    }
    fun signOut(){
        authRemoteDataSource.signOut()
    }
}