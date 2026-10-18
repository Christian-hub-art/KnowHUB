package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import kotlinx.coroutines.flow.Flow


interface ReviewRemoteDataSource {
    // Crear y leer
    suspend fun createReview(review: CreateReviewDto): Unit
    suspend fun getAllReviews(): List<ReviewDto>
    suspend fun getReviewsByUser(userId: String): List<ReviewDto>
    suspend fun getReviewById(id: String, currentUserId: String = ""): ReviewDto


    suspend fun updateReview(id: String, review: CreateReviewDto): Unit
    suspend fun deleteReview(id: String): Unit

    // Comentarios
    suspend fun getReviewComments(id: String): List<ReviewDto>

    // Likes y escucha en tiempo real
    suspend fun sendOrDeleteLike(reviewId: String, userId: String): Unit
    fun listenAllReviews(): Flow<List<ReviewDto>>

}