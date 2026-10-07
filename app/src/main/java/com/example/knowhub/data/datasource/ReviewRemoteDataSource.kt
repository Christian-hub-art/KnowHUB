package com.example.knowhub.data.datasource

import com.example.knowhub.data.dtos.CreateReviewDto
import com.example.knowhub.data.dtos.ReviewDto

interface ReviewRemoteDataSource {
    suspend fun getAllReviews(): List<ReviewDto>
    suspend fun getReviewById(id: String): ReviewDto
    suspend fun createReview(review: CreateReviewDto)
    suspend fun deleteReview(id: String)
    suspend fun updateReview(id: String, review: CreateReviewDto)
    suspend fun getReviewReplies(id: String): List<ReviewDto>
    suspend fun getReviewsByAsignatura(asignaturaId: String): List<ReviewDto>
    suspend fun getReviewsByUsuario(usuarioId: String): List<ReviewDto>
}
