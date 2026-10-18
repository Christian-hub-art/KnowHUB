package com.example.knowhub.data.Services

import com.example.knowhub.data.dtos.RegisterUserDto
import com.example.knowhub.data.dtos.UserProfileDto

interface UserRemoteDataSource {

    suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String): Unit
    suspend fun getUserById(id: String): UserProfileDto
}