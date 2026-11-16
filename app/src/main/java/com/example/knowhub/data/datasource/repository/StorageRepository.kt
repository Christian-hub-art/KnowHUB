package com.example.knowhub.data.datasource.repository

import android.net.Uri
import android.util.Log
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.StorageRemoteDataSource
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

enum class ProfileImageUploadFailureReason {
    NOT_AUTHENTICATED,
    NOT_AUTHORIZED,
    BUCKET_NOT_FOUND,
    QUOTA_EXCEEDED,
    NETWORK,
    CANCELLED,
    PROFILE_UPDATE_FAILED,
    UNKNOWN
}

class ProfileImageUploadException(
    val reason: ProfileImageUploadFailureReason,
    cause: Throwable? = null
) : Exception(cause)

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val auth: AuthRemoteDataSource
) {

    suspend fun uploadProfileImage(uri: Uri): Result<String>{
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(
                ProfileImageUploadException(ProfileImageUploadFailureReason.NOT_AUTHENTICATED)
            )

            val path = "profileImages/$userId.jpg"
            val url = storage.uploadImage(path, uri)

            try {
                auth.updateProfileImage(url)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw ProfileImageUploadException(
                    ProfileImageUploadFailureReason.PROFILE_UPDATE_FAILED,
                    e
                )
            }

            Result.success(url)
        } catch (e: CancellationException) {
            throw e
        } catch (e: ProfileImageUploadException) {
            Result.failure(e)
        } catch (e: StorageException) {
            Log.e(
                "StorageRepository",
                "Profile image upload failed in Firebase Storage (code=${e.errorCode})",
                e
            )
            val reason = when (e.errorCode) {
                StorageException.ERROR_NOT_AUTHENTICATED ->
                    ProfileImageUploadFailureReason.NOT_AUTHENTICATED
                StorageException.ERROR_NOT_AUTHORIZED ->
                    ProfileImageUploadFailureReason.NOT_AUTHORIZED
                StorageException.ERROR_BUCKET_NOT_FOUND,
                StorageException.ERROR_PROJECT_NOT_FOUND ->
                    ProfileImageUploadFailureReason.BUCKET_NOT_FOUND
                StorageException.ERROR_QUOTA_EXCEEDED ->
                    ProfileImageUploadFailureReason.QUOTA_EXCEEDED
                StorageException.ERROR_RETRY_LIMIT_EXCEEDED ->
                    ProfileImageUploadFailureReason.NETWORK
                StorageException.ERROR_CANCELED ->
                    ProfileImageUploadFailureReason.CANCELLED
                else -> ProfileImageUploadFailureReason.UNKNOWN
            }
            Result.failure(ProfileImageUploadException(reason, e))
        } catch (e: Exception) {
            Result.failure(ProfileImageUploadException(ProfileImageUploadFailureReason.UNKNOWN, e))
        }
    }

}