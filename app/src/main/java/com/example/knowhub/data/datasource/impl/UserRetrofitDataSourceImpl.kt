package com.example.knowhub.data.datasource.impl

import com.example.knowhub.data.datasource.UserRemoteDataSource
import com.example.knowhub.data.datasource.services.UserRetrofitService
import com.example.knowhub.data.dtos.ReviewDto
import com.example.knowhub.data.dtos.UserProfileDto
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(private val service: UserRetrofitService): UserRemoteDataSource {
    override suspend fun getUserById(id: String): UserProfileDto {
        return  service.getUsuarioById(id)
    }

    override suspend fun getUserReview(id: String): List<ReviewDto> {
        return service.getReviewByUser(id)
    }
}