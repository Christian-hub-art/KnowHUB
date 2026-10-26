package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.ReviewRemoteDataSource
import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
        return snapshot.documents.map { doc ->
            val review = doc.toObject(ReviewDto::class.java)
            review?.copy(id = doc.id) ?: throw Exception("Reseña no encontrada")
        }
    }

    override suspend fun getReviewsByUser(userId: String): List<ReviewDto> {
        val snapshot = db.collection("reviews").whereEqualTo("userId", userId).get().await()
        return snapshot.documents.map { doc ->
            val review = doc.toObject(ReviewDto::class.java)
            review?.copy(id = doc.id) ?: throw Exception("Reseña no encontrada")
        }
    }

    override suspend fun getReviewById(
        id: String,
        currentUserId: String
    ): ReviewDto {

        val reviewRef = db.collection("reviews").document(id)
        val reviewSnapshot = reviewRef.get().await()
        val review = reviewSnapshot.toObject(ReviewDto::class.java) ?: throw Exception("Review not found")

        if(currentUserId.isNotEmpty()){
            val likeSnapshot = reviewRef.collection("likes").document(currentUserId).get().await()
            val hasliked = likeSnapshot.exists()

            if(hasliked){
                review.liked= true
            }

        }

        return review.copy(id = reviewSnapshot.id)
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
                "descripcion" to review.descripcion,
                "calificacion" to review.calificacion
            )
        ).await()
    }

    override suspend fun deleteReview(id: String) {
        db.collection("reviews").document(id).delete().await()
    }

    override suspend fun sendOrDeleteReviewLike(reviewId: String, userId: String) {
        val reviewRef = db.collection("reviews").document(reviewId)
        val likesRef = reviewRef.collection("likes").document(userId)

        db.runTransaction { transaction ->

            val likeDoc = transaction.get(likesRef)

            if(likeDoc.exists()){
                transaction.delete(likesRef)
                transaction.update(reviewRef, "likes", FieldValue.increment(-1))
            }else{
                transaction.set(likesRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(reviewRef, "likes", FieldValue.increment(1))
            }
        }.await()
    }

    override fun listenAllReviews(): Flow<List<ReviewDto>> = callbackFlow {
        val listener = db.collection("reviews").addSnapshotListener { snapshot, error ->
            if(error!=null){
                close(error)
                return@addSnapshotListener
            }

            if (snapshot!=null){
                val reviews = snapshot.documents.map { doc ->
                    val review = doc.toObject(ReviewDto::class.java)
                    review?.copy(id = doc.id) ?: throw Exception("Reseña no encontrada")
                }
                trySend(reviews).isSuccess
            }

        }

        awaitClose { listener.remove() }

    }

    override fun listenReviewsByGeneralReview(generalReviewId: Int): Flow<List<ReviewDto>> = callbackFlow {
        val listener = db.collection("reviews").whereEqualTo("generalReviewId", generalReviewId).addSnapshotListener { snapshot, error ->
            if(error!=null){
                close(error)
                return@addSnapshotListener
            }

            if (snapshot!=null){
                val reviews = snapshot.documents.map { doc ->
                    val review = doc.toObject(ReviewDto::class.java)
                    review?.copy(id = doc.id) ?: throw Exception("Reseña no encontrada")
                }
                trySend(reviews).isSuccess
            }

        }

        awaitClose { listener.remove() }

    }
}
