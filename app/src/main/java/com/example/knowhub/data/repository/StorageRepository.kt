package com.example.knowhub.data.repository

import android.net.Uri
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.StorageRemoteDataSource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.storage.StorageException
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val auth: AuthRemoteDataSource
) {
    suspend fun uploadProfileImage(uri: Uri): Result<String> {
        val userId = auth.currentUser?.uid
            ?: return Result.failure(Exception("No hay una sesión iniciada."))

        return try {
            val path = "profileImages/$userId.jpg"
            val url = storage.uploadImage(path, uri)
            auth.updateProfileImage(url)
            Result.success(url)
        } catch (error: FirebaseNetworkException) {
            Result.failure(Exception("Sin conexión a Internet. Revisa tu conexión e inténtalo de nuevo.", error))
        } catch (error: StorageException) {
            val message = when (error.errorCode) {
                StorageException.ERROR_NOT_AUTHENTICATED -> "Tu sesión expiró. Inicia sesión nuevamente."
                StorageException.ERROR_NOT_AUTHORIZED -> "No tienes permiso para subir esta imagen."
                StorageException.ERROR_QUOTA_EXCEEDED -> "Se alcanzó el límite de almacenamiento disponible."
                StorageException.ERROR_CANCELED -> "La carga de la imagen fue cancelada."
                else -> "No se pudo subir la imagen. Inténtalo de nuevo."
            }
            Result.failure(Exception(message, error))
        } catch (error: FirebaseAuthException) {
            Result.failure(
                Exception("La imagen se subió, pero no se pudo actualizar tu perfil. Inicia sesión nuevamente.", error)
            )
        } catch (error: Exception) {
            Result.failure(Exception("Ocurrió un error al cambiar la foto de perfil. Inténtalo de nuevo.", error))
        }
    }
}
