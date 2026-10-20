package com.example.knowhub.data.datasource

import android.R
import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.google.firebase.firestore.auth.User

interface UserRemoteDataSource {

    suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String): Unit
    suspend fun getUserById(id: String, currentUserId: String): UserProfileDto
    suspend fun updateUserProfileImage(userId: String, photoUrl: String): Unit

    suspend fun followOrUnfollowUser(currentUserId: String, targetUserId: String): Unit
}