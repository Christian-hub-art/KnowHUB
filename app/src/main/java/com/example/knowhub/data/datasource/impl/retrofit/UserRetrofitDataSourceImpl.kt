package com.example.knowhub.data.datasource.impl.retrofit

import com.example.knowhub.data.datasource.UserRemoteDataSource
import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.dtos.UserSummaryDto
import com.example.knowhub.data.datasource.services.UserRetrofitService
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(
    private val service: UserRetrofitService
) : UserRemoteDataSource {
    override suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String) {
        service.registerUser(userId, registerUserDto)
    }

    override suspend fun getUserById(id: String, currentUserId: String): UserProfileDto =
        service.getUserById(id, currentUserId)

    override suspend fun updateUserProfileImage(userId: String, photoUrl: String) {
        service.updateUserProfileImage(userId, photoUrl)
    }

    override suspend fun followOrUnfollowUser(currentUserId: String, targetUserId: String) {
        service.followOrUnfollowUser(currentUserId, targetUserId)
    }

    override suspend fun getConnections(userId: String, followers: Boolean): List<UserSummaryDto> =
        service.getConnections(userId, if (followers) "followers" else "following")

    override suspend fun getFollowingIds(userId: String): Set<String> =
        service.getFollowingIds(userId)
}