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
        val snapshot = db.collection("reviews").get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(ReviewDto::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun getReviewsByUser(userId: String): List<ReviewDto> {
        val snapshot = db.collection("reviews").whereEqualTo("userId", userId).get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(ReviewDto::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun getReviewById(
        id: String,
        currentUserId: String
    ): ReviewDto {
        val doc = db.collection("reviews").document(id).get().await()
        return doc.toObject(ReviewDto::class.java)?.copy(id = doc.id)
            ?: throw Exception("Reseña no encontrada")

    }

    override suspend fun updateReview(
        id: String,
        review: CreateReviewDto
    ) {
        // update en vez de set: así no se pierden likes, comentarios, fecha ni autor
        db.collection("reviews").document(id).update(
            mapOf(
                "clase" to review.clase,
                "nombreAsignatura" to review.nombreAsignatura,
                "nombreProfesor" to review.nombreProfesor,
                "descripcion" to review.descripcion
            )
        ).await()
    }

    override suspend fun deleteReview(id: String) {
        db.collection("reviews").document(id).delete().await()
    }

    override suspend fun sendOrDeleteLike(reviewId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override fun listenAllReviews(): Flow<List<ReviewDto>> {
        TODO("Not yet implemented")
    }
}