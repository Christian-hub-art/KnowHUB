package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.ReviewRemoteDataSource
import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): ReviewRemoteDataSource {


    override suspend fun createReview(review: CreateReviewDto) {
        db.collection("reviews").add(review).await()
    }

    override suspend fun getAllReviews(): List<ReviewDto> {
        return db.collection("reviews").get().await().toObjects(ReviewDto::class.java)
    }

    override suspend fun getReviewById(
        id: String,
        currentUserId: String
    ): ReviewDto {
        return db.collection("reviews").document(id).get().await().toObject(ReviewDto::class.java) ?: throw Exception("Review not found")

    }

    override suspend fun updateReview(
        id: String,
        review: CreateReviewDto
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReview(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewComments(id: String): List<ReviewDto> {
        TODO("Not yet implemented")
    }

    override suspend fun sendOrDeleteLike(reviewId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override fun listenAllReviews(): Flow<List<ReviewDto>> {
        TODO("Not yet implemented")
    }
}