package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.ReviewRemoteDataSource
import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): ReviewRemoteDataSource {


    override suspend fun createReview(review: CreateReviewDto) {
        db.collection("reviews").add(review).await()
    }

    override suspend fun getAllReviews(currentUserId: String): List<ReviewDto> {
        val snapshot = db.collection("reviews").get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(ReviewDto::class.java)?.copy(id = doc.id)
        }.map { it.withCurrentUserInteraction(currentUserId) }
    }

    override suspend fun getReviewsByUser(userId: String, currentUserId: String): List<ReviewDto> {
        val snapshot = db.collection("reviews").whereEqualTo("userId", userId).get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(ReviewDto::class.java)?.copy(id = doc.id)
        }.map { it.withCurrentUserInteraction(currentUserId) }
    }

    override suspend fun getReviewById(
        id: String,
        currentUserId: String
    ): ReviewDto {
        val doc = db.collection("reviews").document(id).get().await()
        val review = doc.toObject(ReviewDto::class.java)?.copy(id = doc.id)
            ?: throw Exception("Reseña no encontrada")
        return review.withCurrentUserInteraction(currentUserId)
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

    override suspend fun rateReview(reviewId: String, userId: String, rating: Int) {
        require(userId.isNotBlank()) { "Se requiere iniciar sesión para calificar." }
        require(rating in 1..5) { "La calificación debe estar entre 1 y 5 estrellas." }

        val reviewRef = db.collection("reviews").document(reviewId)
        val ratingRef = reviewRef.collection("ratings").document(userId)
        db.runTransaction { transaction ->
            val reviewSnapshot = transaction.get(reviewRef)
            check(reviewSnapshot.exists()) { "La reseña ya no existe." }
            check(reviewSnapshot.getString("userId") != userId) {
                "No puedes calificar tu propia reseña."
            }
            val previousRating = transaction.get(ratingRef).getLong("rating")?.toInt()
            val previousTotal = reviewSnapshot.getLong("ratingTotal") ?: 0L
            val previousCount = reviewSnapshot.getLong("ratingCount")?.toInt() ?: 0
            val ownerId = reviewSnapshot.getString("userId").orEmpty()
            val notificationRef = if (previousRating == null && ownerId.isNotBlank()) {
                db.collection("users").document(ownerId).collection("notifications").document()
            } else {
                null
            }

            val nextTotal = previousTotal - (previousRating ?: 0) + rating
            val nextCount = previousCount + if (previousRating == null) 1 else 0

            transaction.set(
                ratingRef,
                mapOf(
                    "rating" to rating,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )
            transaction.update(
                reviewRef,
                mapOf(
                    "ratingTotal" to nextTotal,
                    "ratingCount" to nextCount
                )
            )
            if (notificationRef != null) {
                transaction.set(
                    notificationRef,
                    mapOf(
                        "type" to "rating",
                        "title" to "Nueva calificación",
                        "body" to "Alguien calificó tu reseña con $rating estrellas.",
                        "actorId" to userId,
                        "reviewId" to reviewId,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "read" to false
                    )
                )
            }
        }.await()
    }

    override suspend fun toggleLike(reviewId: String, userId: String) {
        require(userId.isNotBlank()) { "Inicia sesión para dar me gusta a una reseña." }

        val reviewRef = db.collection("reviews").document(reviewId)
        val likeRef = reviewRef.collection("likes").document(userId)

        db.runTransaction { transaction ->
            val like = transaction.get(likeRef)
            if (like.exists()) {
                transaction.delete(likeRef)
                transaction.update(reviewRef, "likes", FieldValue.increment(-1))
            } else {
                transaction.set(
                    likeRef,
                    mapOf("timestamp" to FieldValue.serverTimestamp())
                )
                transaction.update(reviewRef, "likes", FieldValue.increment(1))
            }
        }.await()
    }

    override fun listenAllReviews(currentUserId: String): Flow<List<ReviewDto>> = callbackFlow {
        val registration = db.collection("reviews").addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            } else if (snapshot != null) {
                launch {
                    try {
                        val reviews = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(ReviewDto::class.java)?.copy(id = doc.id)
                        }
                            .map { it.withCurrentUserInteraction(currentUserId) }
                        trySend(reviews).isSuccess
                    } catch (exception: Exception) {
                        close(exception)
                    }
                }
            }
        }
        awaitClose { registration.remove() }
    }

    override fun listenReviewsByUser(
        userId: String,
        currentUserId: String
    ): Flow<List<ReviewDto>> = callbackFlow {
        val registration = db.collection("reviews")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    launch {
                        try {
                            val reviews = snapshot.documents.mapNotNull { doc ->
                                doc.toObject(ReviewDto::class.java)?.copy(id = doc.id)
                            }
                                .map { it.withCurrentUserInteraction(currentUserId) }
                            trySend(reviews).isSuccess
                        } catch (exception: Exception) {
                            close(exception)
                        }
                    }
                }
            }
        awaitClose { registration.remove() }
    }

    override fun listenReviewById(
        id: String,
        currentUserId: String
    ): Flow<ReviewDto?> = callbackFlow {
        val registration = db.collection("reviews").document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    launch {
                        try {
                            val review = snapshot.toObject(ReviewDto::class.java)
                                ?.copy(id = snapshot.id)
                                ?.withCurrentUserInteraction(currentUserId)
                            trySend(review).isSuccess
                        } catch (exception: Exception) {
                            close(exception)
                        }
                    }
                }
            }
        awaitClose { registration.remove() }
    }

    private suspend fun ReviewDto.withCurrentUserInteraction(userId: String): ReviewDto {
        if (userId.isBlank()) return this
        val reviewRef = db.collection("reviews").document(id)
        val rating = reviewRef.collection("ratings").document(userId)
            .get().await().getLong("rating")?.toInt() ?: 0
        val liked = reviewRef.collection("likes").document(userId)
            .get().await().exists()
        return copy(userRating = rating, likedByCurrentUser = liked)
    }
}