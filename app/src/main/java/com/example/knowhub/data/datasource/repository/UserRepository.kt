package com.example.knowhub.data.datasource.repository

import android.util.Log
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.UserRemoteDataSource
import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.dtos.UserSummaryDto
import com.example.knowhub.data.datasource.services.NotificationTokenStore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource

){

    suspend fun registerUser(name: String, userName: String, userId: String): Result<Unit>{
        return try {
            val fcmToken= FirebaseMessaging.getInstance().token.await()
            val registerUserDto = RegisterUserDto(name,userName, fcmToken)
            userRemoteDataSource.registerUser(registerUserDto, userId)
            NotificationTokenStore.save(fcmToken)
            Result.success(Unit)
        }catch (e: Exception){
            Log.d("TAG","getUserById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getUserById(id: String, currentUserId: String = ""): Result<UserProfileDto> {
        return try {
            val viewerId = currentUserId.ifBlank { authRemoteDataSource.currentUser?.uid.orEmpty() }
            Result.success(userRemoteDataSource.getUserById(id, viewerId))
        } catch (e: Exception) {
            Log.d("TAG", "getUserById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateUserProfileImage(userId: String, photoUrl: String): Result<Unit> {
        return try {
            userRemoteDataSource.updateUserProfileImage(userId, photoUrl)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "updateUserProfileImage: ${e.message}")
            Result.failure(e)
        }
    }
    suspend fun followOrUnfollowUser(currentUserId: String, targetUserId: String): Result<Unit> {
        return try {
            val authenticatedUserId = authRemoteDataSource.currentUser?.uid
                ?: throw IllegalStateException("Inicia sesión para seguir a otro usuario.")
            require(authenticatedUserId == currentUserId) {
                "El usuario autenticado no coincide con la cuenta que intenta seguir."
            }
            userRemoteDataSource.followOrUnfollowUser(currentUserId, targetUserId)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("followOrUnfollowUser", "follow toggle failed: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getConnections(userId: String, followers: Boolean): Result<List<UserSummaryDto>> =
        try {
            Result.success(userRemoteDataSource.getConnections(userId, followers))
        } catch (e: Exception) {
            Log.d("UserRepository", "getConnections: ${e.message}")
            Result.failure(e)
        }

    suspend fun getFollowingIds(): Result<Set<String>> {
        val userId = authRemoteDataSource.currentUser?.uid
            ?: return Result.failure(IllegalStateException("Inicia sesión para ver el feed de seguidos."))
        return try {
            Result.success(userRemoteDataSource.getFollowingIds(userId))
        } catch (e: Exception) {
            Log.d("UserRepository", "getFollowingIds: ${e.message}")
            Result.failure(e)
        }
    }
}
