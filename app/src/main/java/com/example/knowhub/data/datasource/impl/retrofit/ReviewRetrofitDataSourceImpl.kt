package com.example.knowhub.data.datasource.impl.retrofit

import com.example.knowhub.data.datasource.ReviewRemoteDataSource
import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewRatingDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import com.example.knowhub.data.datasource.services.ReviewRetrofitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReviewRetrofitDataSourceImpl @Inject constructor(
    private val service: ReviewRetrofitService
) : ReviewRemoteDataSource {
    override suspend fun createReview(review: CreateReviewDto) {
        service.createReview(review)
    }

    override suspend fun getAllReviews(currentUserId: String): List<ReviewDto> =
        service.getAllReviews(currentUserId)

    override suspend fun getReviewsByUser(userId: String, currentUserId: String): List<ReviewDto> =
        service.getReviewsByUser(userId, currentUserId)

    override suspend fun getReviewById(id: String, currentUserId: String): ReviewDto =
        service.getReviewById(id, currentUserId)

    override suspend fun updateReview(id: String, review: CreateReviewDto) {
        service.updateReview(id, review)
    }

    override suspend fun deleteReview(id: String) {
        service.deleteReview(id)
    }

    override suspend fun rateReview(reviewId: String, userId: String, rating: Int) {
        service.rateReview(reviewId, userId, ReviewRatingDto(rating))
    }

    override suspend fun toggleLike(reviewId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override fun listenAllReviews(currentUserId: String): Flow<List<ReviewDto>> = flow {
        emit(service.getAllReviews(currentUserId))
    }

    override fun listenReviewsByUser(
        userId: String,
        currentUserId: String
    ): Flow<List<ReviewDto>> = TODO("Not yet implemented")

    override fun listenReviewById(
        id: String,
        currentUserId: String
    ): Flow<ReviewDto?> = TODO("Not yet implemented")
}
