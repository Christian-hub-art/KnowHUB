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
import kotlin.math.roundToInt
import javax.inject.Inject

class ReviewFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): ReviewRemoteDataSource {


    override suspend fun createReview(review: CreateReviewDto) {
        val reviewRef = db.collection("reviews").document()
        val materiaRef = db.collection("materias").document(review.generalReviewId.toString())

        db.runTransaction { transaction ->
            val materiaDoc = transaction.get(materiaRef)

            transaction.set(reviewRef, review)

            if (materiaDoc.exists()) {
                val cantidad = (materiaDoc.getLong("cantidadReviews") ?: 0L) + 1
                val suma = (materiaDoc.getLong("sumaCalificaciones") ?: 0L) + review.calificacion
                transaction.update(
                    materiaRef,
                    mapOf(
                        "cantidadReviews" to cantidad,
                        "sumaCalificaciones" to suma,
                        "calificacionMedia" to (suma.toDouble() / cantidad).roundToInt()
                    )
                )
            }
        }.await()
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
        val reviewRef = db.collection("reviews").document(id)

        db.runTransaction { transaction ->
            val reviewDoc = transaction.get(reviewRef)
            val calificacionAnterior = reviewDoc.getLong("calificacion") ?: 0L
            val generalReviewId = reviewDoc.getLong("generalReviewId") ?: 0L
            val materiaRef = db.collection("materias").document(generalReviewId.toString())
            val materiaDoc = transaction.get(materiaRef)

            // update en vez de set: así no se pierden likes, comentarios, fecha ni autor
            transaction.update(
                reviewRef,
                mapOf(
                    "clase" to review.clase,
                    "nombreAsignatura" to review.nombreAsignatura,
                    "nombreProfesor" to review.nombreProfesor,
                    "descripcion" to review.descripcion,
                    "calificacion" to review.calificacion
                )
            )

            if (materiaDoc.exists()) {
                val cantidad = materiaDoc.getLong("cantidadReviews") ?: 0L
                val suma = (materiaDoc.getLong("sumaCalificaciones") ?: 0L) - calificacionAnterior + review.calificacion
                transaction.update(
                    materiaRef,
                    mapOf(
                        "sumaCalificaciones" to suma,
                        "calificacionMedia" to if (cantidad > 0) (suma.toDouble() / cantidad).roundToInt() else 0
                    )
                )
            }
        }.await()
    }

    override suspend fun deleteReview(id: String) {
        val reviewRef = db.collection("reviews").document(id)

        db.runTransaction { transaction ->
            val reviewDoc = transaction.get(reviewRef)
            val calificacion = reviewDoc.getLong("calificacion") ?: 0L
            val generalReviewId = reviewDoc.getLong("generalReviewId") ?: 0L
            val materiaRef = db.collection("materias").document(generalReviewId.toString())
            val materiaDoc = transaction.get(materiaRef)

            transaction.delete(reviewRef)

            if (reviewDoc.exists() && materiaDoc.exists()) {
                val cantidad = ((materiaDoc.getLong("cantidadReviews") ?: 0L) - 1).coerceAtLeast(0L)
                val suma = ((materiaDoc.getLong("sumaCalificaciones") ?: 0L) - calificacion).coerceAtLeast(0L)
                transaction.update(
                    materiaRef,
                    mapOf(
                        "cantidadReviews" to cantidad,
                        "sumaCalificaciones" to suma,
                        "calificacionMedia" to if (cantidad > 0) (suma.toDouble() / cantidad).roundToInt() else 0
                    )
                )
            }
        }.await()
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
