package com.example.knowhub.data.datasource.services

import com.example.knowhub.data.datasource.dtos.RegisterUserDto
import com.example.knowhub.data.datasource.dtos.UserProfileDto
import com.example.knowhub.data.datasource.dtos.UserSummaryDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserRetrofitService {
    @POST("users/{userId}")
    suspend fun registerUser(
        @Path("userId") userId: String,
        @Body registerUserDto: RegisterUserDto
    )

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: String,
        @Query("currentUserId") currentUserId: String = ""
    ): UserProfileDto

    @PATCH("users/{userId}/profile-image")
    suspend fun updateUserProfileImage(
        @Path("userId") userId: String,
        @Query("photoUrl") photoUrl: String
    )

    @POST("users/{currentUserId}/following/{targetUserId}")
    suspend fun followOrUnfollowUser(
        @Path("currentUserId") currentUserId: String,
        @Path("targetUserId") targetUserId: String
    )

    @GET("users/{userId}/{relationship}")
    suspend fun getConnections(
        @Path("userId") userId: String,
        @Path("relationship") relationship: String
    ): List<UserSummaryDto>

    @GET("users/{userId}/following/ids")
    suspend fun getFollowingIds(@Path("userId") userId: String): Set<String>
}
