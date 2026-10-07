package com.example.knowhub.data.datasource.impl.retrofit

import com.example.knowhub.data.datasource.ReviewRemoteDataSource
import com.example.knowhub.data.datasource.services.ReviewRetrofitService
import com.example.knowhub.data.dtos.CreateReviewDto
import com.example.knowhub.data.dtos.ReviewDto
import javax.inject.Inject

class ReviewRetrofitDataSourceImpl @Inject constructor(
    private val service: ReviewRetrofitService,
) : ReviewRemoteDataSource {

    override suspend fun getAllReviews(): List<ReviewDto> {
        return service.getAllReviews()
    }

    override suspend fun getReviewById(id: String): ReviewDto {
        return service.getReviewById(id)
    }

    override suspend fun createReview(review: CreateReviewDto) {
        service.createReview(review)
    }

    override suspend fun deleteReview(id: String) {
        service.deleteReview(id)
    }

    override suspend fun updateReview(id: String, review: CreateReviewDto) {
        service.updateReview(id, review)
    }

    override suspend fun getReviewReplies(id: String): List<ReviewDto> {
        return service.getReviewReplies(id)
    }

    override suspend fun getReviewsByAsignatura(asignaturaId: String): List<ReviewDto> {
        return service.getReviewsByAsignatura(asignaturaId)
    }

    override suspend fun getReviewsByUsuario(usuarioId: String): List<ReviewDto> {
        return service.getReviewsByUsuario(usuarioId)
    }
}
