package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import kotlinx.coroutines.flow.Flow


interface ReviewRemoteDataSource {
    suspend fun createReview(review: CreateReviewDto): Unit
    suspend fun getAllReviews(): List<ReviewDto>
    suspend fun getReviewsByUser(userId: String): List<ReviewDto>
    suspend fun getReviewById(id: String, currentUserId: String = ""): ReviewDto


    suspend fun updateReview(id: String, review: CreateReviewDto): Unit
    suspend fun deleteReview(id: String): Unit

    suspend fun sendOrDeleteReviewLike(reviewId: String, userId: String): Unit

    fun listenAllReviews(): Flow<List<ReviewDto>>

    fun listenReviewsByGeneralReview(generalReviewId: Int): Flow<List<ReviewDto>>


}