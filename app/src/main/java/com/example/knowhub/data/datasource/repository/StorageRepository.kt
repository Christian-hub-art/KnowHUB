package com.example.knowhub.data.datasource.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.knowhub.R
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.StorageRemoteDataSource
import com.google.firebase.storage.StorageException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val auth: AuthRemoteDataSource,
    @ApplicationContext private val context: Context   // para leer los mensajes de strings.xml
) {

    suspend fun uploadProfileImage(uri: Uri): Result<String>{
        return try {
            val userId = auth.currentUser?.uid
                ?: return Result.failure(Exception(context.getString(R.string.profile_photo_error_not_authenticated)))

            val path = "profileImages/$userId.jpg"
            val url = storage.uploadImage(path, uri)

            try {
                auth.updateProfileImage(url)
            } catch (e: Exception) {
                return Result.failure(Exception(context.getString(R.string.profile_photo_error_auth_update)))
            }

            Result.success(url)
        } catch (e: StorageException) {
            Log.e("StorageRepository", "Error al subir la foto (code=${e.errorCode})", e)
            val mensaje = when (e.errorCode) {
                StorageException.ERROR_NOT_AUTHENTICATED ->
                    context.getString(R.string.profile_photo_error_not_authenticated)
                StorageException.ERROR_NOT_AUTHORIZED ->
                    context.getString(R.string.profile_photo_error_not_authorized)
                StorageException.ERROR_BUCKET_NOT_FOUND,
                StorageException.ERROR_PROJECT_NOT_FOUND ->
                    context.getString(R.string.profile_photo_error_bucket)
                StorageException.ERROR_QUOTA_EXCEEDED ->
                    context.getString(R.string.profile_photo_error_quota)
                StorageException.ERROR_RETRY_LIMIT_EXCEEDED ->
                    context.getString(R.string.profile_photo_error_network)
                StorageException.ERROR_CANCELED ->
                    context.getString(R.string.profile_photo_error_cancelled)
                else -> context.getString(R.string.profile_photo_error_generic)
            }
            Result.failure(Exception(mensaje))
        } catch (e: Exception) {
            Result.failure(Exception(context.getString(R.string.profile_photo_error_generic)))
        }
    }

}