package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.dtos.UserSummaryDto

interface UserRemoteDataSource {

    suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String): Unit
    suspend fun getUserById(id: String, currentUserId: String = ""): UserProfileDto
    suspend fun updateUserProfileImage(userId: String, photoUrl: String): Unit
    suspend fun followOrUnfollowUser(currentUserId: String, targetUserId: String): Unit
    suspend fun getConnections(userId: String, followers: Boolean): List<UserSummaryDto>
    suspend fun getFollowingIds(userId: String): Set<String>
}