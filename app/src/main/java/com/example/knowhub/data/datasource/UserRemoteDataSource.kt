package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto

interface UserRemoteDataSource {

    suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String): Unit
    suspend fun getUserById(id: String): UserProfileDto
    suspend fun updateUserProfileImage(userId: String, photoUrl: String): Unit
}