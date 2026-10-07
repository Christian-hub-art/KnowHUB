package com.example.knowhub.data.datasource

import com.example.knowhub.data.dtos.ReviewDto
import com.example.knowhub.data.dtos.UserProfileDto

interface UserRemoteDataSource {
    suspend fun getUserById(id: String): UserProfileDto
    suspend fun getUserReview(id: String): List<ReviewDto>
}