package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.CommentRemoteDataSource
import com.example.knowhub.data.datasource.dtos.CommentDto
import com.example.knowhub.data.datasource.dtos.CreateCommentDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommentFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CommentRemoteDataSource {

    override suspend fun createComment(comment: CreateCommentDto) {
        db.collection("comments").add(comment).await()
        // Mantiene actualizado el contador de respuestas de la reseña
        db.collection("reviews").document(comment.reviewId)
            .update("cantidadComentarios", FieldValue.increment(1)).await()
    }

    override suspend fun getCommentsByReview(reviewId: String): List<CommentDto> {
        val snapshot = db.collection("comments").whereEqualTo("reviewId", reviewId).get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(CommentDto::class.java)?.copy(id = doc.id)
        }
    }
}
