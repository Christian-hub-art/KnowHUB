package com.example.knowhub.data.repository

import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    val currentUser: FirebaseUser?
        get() = authRemoteDataSource.currentUser

    suspend fun signIn(correoElectronico: String, contrasena: String): Result<Unit> {
        return try {
            authRemoteDataSource.signIn(correoElectronico, contrasena)
            Result.success(Unit)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Credenciales incorrectas"))
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("El usuario no existe"))
        } catch (e: Exception) {
            Result.failure(Exception("Error al iniciar sesión"))
        }
    }

    suspend fun signUp(correoElectronico: String, contrasena: String): Result<Unit> {
        return try {
            authRemoteDataSource.signUp(correoElectronico, contrasena)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Error al registrar usuario"))
        }
    }
    fun signOut(){
        authRemoteDataSource.signOut()
    }

    suspend fun updateProfileImage(photoUrl: String) {
        authRemoteDataSource.updateProfileImage(photoUrl)
    }

    suspend fun refreshProfileImage(): Result<String?> = try {
        Result.success(authRemoteDataSource.refreshProfileImage())
    } catch (e: FirebaseAuthInvalidUserException) {
        Result.failure(Exception("La sesión ya no es válida. Inicia sesión nuevamente."))
    } catch (e: Exception) {
        Result.failure(Exception("No se pudo cargar la foto de perfil."))
    }
}
