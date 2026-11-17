package com.example.knowhub.data.datasource.repository

import android.net.Uri
import android.util.Log
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.StorageRemoteDataSource
import com.google.firebase.storage.StorageException
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val auth: AuthRemoteDataSource
) {

    suspend fun uploadProfileImage(uri: Uri): Result<String>{
        return try {
            val userId = auth.currentUser?.uid
                ?: return Result.failure(Exception("Tu sesión no está activa. Inicia sesión e inténtalo de nuevo."))

            val path = "profileImages/$userId.jpg"
            val url = storage.uploadImage(path, uri)

            try {
                auth.updateProfileImage(url)
            } catch (e: Exception) {
                return Result.failure(Exception("La foto se subió, pero no se pudo actualizar tu perfil de autenticación."))
            }

            Result.success(url)
        } catch (e: StorageException) {
            Log.e("StorageRepository", "Error al subir la foto (code=${e.errorCode})", e)
            val mensaje = when (e.errorCode) {
                StorageException.ERROR_NOT_AUTHENTICATED ->
                    "Tu sesión no está activa. Inicia sesión e inténtalo de nuevo."
                StorageException.ERROR_NOT_AUTHORIZED ->
                    "No se pudo actualizar tu foto de perfil. Inténtalo más tarde."
                StorageException.ERROR_BUCKET_NOT_FOUND,
                StorageException.ERROR_PROJECT_NOT_FOUND ->
                    "No se encontró el almacenamiento de Firebase. Revisa la configuración del bucket."
                StorageException.ERROR_QUOTA_EXCEEDED ->
                    "Se excedió la cuota de almacenamiento de Firebase."
                StorageException.ERROR_RETRY_LIMIT_EXCEEDED ->
                    "No se pudo conectar con Firebase Storage. Revisa tu conexión e inténtalo otra vez."
                StorageException.ERROR_CANCELED ->
                    "Se canceló la subida de la foto."
                else -> "No se pudo subir la foto. Inténtalo de nuevo."
            }
            Result.failure(Exception(mensaje))
        } catch (e: Exception) {
            Result.failure(Exception("No se pudo subir la foto. Inténtalo de nuevo."))
        }
    }

}