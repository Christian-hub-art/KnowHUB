package com.example.knowhub.data.repository

import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    val currentUser: FirebaseUser? = authRemoteDataSource.currentUser

    suspend fun signIn( correoElectronico: String, contrasena: String ): Result<Unit> {
        return try {
            authRemoteDataSource.signIn(
                correoElectronico, contrasena )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e) }
    }

    suspend fun signUp( correoElectronico: String, contrasena: String ): Result<Unit> {
        return try
        { authRemoteDataSource.signUp(
            correoElectronico, contrasena )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e) }
    }
    fun signOut(){
        authRemoteDataSource.signOut()
    }
}