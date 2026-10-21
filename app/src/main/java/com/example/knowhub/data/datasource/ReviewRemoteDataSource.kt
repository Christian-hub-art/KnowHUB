package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import kotlinx.coroutines.flow.Flow


interface ReviewRemoteDataSource {
    suspend fun createReview(review: CreateReviewDto): Unit
    suspend fun getAllReviews(currentUserId: String = ""): List<ReviewDto>
    suspend fun getReviewsByUser(userId: String, currentUserId: String = ""): List<ReviewDto>
    suspend fun getReviewById(id: String, currentUserId: String = ""): ReviewDto


    suspend fun updateReview(id: String, review: CreateReviewDto): Unit
    suspend fun deleteReview(id: String): Unit

    suspend fun rateReview(reviewId: String, userId: String, rating: Int): Unit
    suspend fun toggleLike(reviewId: String, userId: String): Unit
    fun listenAllReviews(currentUserId: String = ""): Flow<List<ReviewDto>>
    fun listenReviewsByUser(userId: String, currentUserId: String = ""): Flow<List<ReviewDto>>
    fun listenReviewById(id: String, currentUserId: String = ""): Flow<ReviewDto?>

}