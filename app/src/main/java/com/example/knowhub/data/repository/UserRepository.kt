package com.example.knowhub.data.repository

import android.util.Log
import com.example.knowhub.data.Services.UserRemoteDataSource
import com.example.knowhub.data.dtos.RegisterUserDto
import com.example.knowhub.data.dtos.UserProfileDto
import com.example.knowhub.data.impl.firestore.UserFirestoreDataSourceImpl
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserFirestoreDataSourceImpl

){

    suspend fun registerUser(name: String, userName: String, userId: String): Result<Unit>{
        return try {
            val registerUserDto = RegisterUserDto(name,userName)
            userRemoteDataSource.registerUser(registerUserDto, userId)
            Result.success(Unit)
        }catch (e: Exception){
            Log.d("TAG","getUserById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getUserById(id: String, currentUserId: String = ""): Result<UserProfileDto> {
        return try {
            val user = userRemoteDataSource.getUserById(id)

            if(user == null) return Result.failure(NoSuchElementException("Usuario $id no encontrado"))

            val userProfileInfo = user

            Result.success(userProfileInfo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
