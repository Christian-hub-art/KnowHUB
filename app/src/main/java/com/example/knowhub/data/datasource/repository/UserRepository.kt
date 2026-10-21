package com.example.knowhub.data.datasource.repository

import android.util.Log
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UpdateUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserFirestoreDataSourceImpl,
    private val authRemoteDataSource: AuthRemoteDataSource

){


    suspend fun registerUser(name: String, userName: String, userId: String): Result<Unit>{
        return try {
            val fcmToken = FirebaseMessaging.getInstance().token.await()
            val registerUserDto = RegisterUserDto(name,userName, fcmToken)
            userRemoteDataSource.registerUser(registerUserDto, userId)
            Result.success(Unit)
        }catch (e: Exception){
            Log.d("TAG","getUserById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getUserById(id: String, currentUserId: String = ""): Result<UserProfileDto> {

        val currentUserId = authRemoteDataSource.currentUser?.uid ?: ""

        return try {
            val user = userRemoteDataSource.getUserById(id, currentUserId)

            if(user == null) return Result.failure(NoSuchElementException("Usuario $id no encontrado"))

            val userProfileInfo = user

            Result.success(userProfileInfo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserProfile(userId: String, updateUserDto: UpdateUserDto): Result<Unit> {
        return try {
            userRemoteDataSource.updateUserProfile(userId, updateUserDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "updateUserProfile: ${e.message}")
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


    suspend fun followOrUnfollowUser(currentUserId: String, targetUserId: String): Result<Unit>{
        return  try{
            userRemoteDataSource.followOrUnfollowUser(currentUserId,targetUserId)
            Result.success(Unit)
        }catch (e: Exception){
            Log.d("followOrUnfollowUser", "getUserById: ${e.message} ")
            Result.failure(e)
        }
    }
}
