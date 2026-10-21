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
        if (comment.parentCommentId != null) {
            db.collection("comments").document(comment.parentCommentId)
                .update("cantidadComentarios", FieldValue.increment(1)).await()
        } else {
            db.collection("reviews").document(comment.reviewId)
                .update("cantidadComentarios", FieldValue.increment(1)).await()
        }
    }


    override suspend fun getCommentsByReview(reviewId: String, currentUserId: String): List<CommentDto> {
        val snapshot = db.collection("comments").whereEqualTo("reviewId", reviewId).get().await()
        return snapshot.documents.map { doc ->
            val comment = doc.toObject(CommentDto::class.java)
            comment?.copy(id = doc.id) ?: throw Exception("Comentario no encontrado")
        }.filter { it.parentCommentId == null }
            .map { marcarLike(it, currentUserId) }
    }

    override suspend fun getCommentById(id: String, currentUserId: String): CommentDto {
        val doc = db.collection("comments").document(id).get().await()
        val comment = doc.toObject(CommentDto::class.java)?.copy(id = doc.id)
            ?: throw Exception("Comentario no encontrado")
        return marcarLike(comment, currentUserId)
    }

    override suspend fun getReplies(commentId: String, currentUserId: String): List<CommentDto> {val snapshot = db.collection("comments").whereEqualTo("parentCommentId", commentId).get().await()
        return snapshot.documents.map { doc ->
            val comment = doc.toObject(CommentDto::class.java)
            comment?.copy(id = doc.id) ?: throw Exception("Comentario no encontrado")
        }.map { marcarLike(it, currentUserId) }
    }

    private suspend fun marcarLike(comment: CommentDto, currentUserId: String): CommentDto {
        if (currentUserId.isNotEmpty()) {
            val likeSnapshot = db.collection("comments").document(comment.id)
                .collection("likes").document(currentUserId).get().await()
            if (likeSnapshot.exists()) {
                comment.liked = true
            }
        }
        return comment
    }

    override suspend fun deleteComment(id: String) {
        val comment = getCommentById(id)
        deleteThread(id)

        if (comment.parentCommentId != null) {
            val parentRef = db.collection("comments").document(comment.parentCommentId)
            if (parentRef.get().await().exists()) {
                parentRef.update("cantidadComentarios", FieldValue.increment(-1)).await()
            }
        } else {
            val reviewRef = db.collection("reviews").document(comment.reviewId)
            if (reviewRef.get().await().exists()) {
                reviewRef.update("cantidadComentarios", FieldValue.increment(-1)).await()
            }
        }
    }

    private suspend fun deleteThread(id: String) {
        db.collection("comments").document(id).delete().await()

        val replies = db.collection("comments").whereEqualTo("parentCommentId", id).get().await()
        for (doc in replies.documents) {
            deleteThread(doc.id)
        }
    }

    override suspend fun deleteCommentsByReview(reviewId: String) {
        val replies = getCommentsByReview(reviewId)
        for (reply in replies) {
            deleteThread(reply.id)
        }
    }

    override suspend fun sendOrDeleteCommentLike(commentId: String, userId: String) {
        val commentRef = db.collection("comments").document(commentId)
        val likesRef = commentRef.collection("likes").document(userId)

        db.runTransaction { transaction ->

            val likeDoc = transaction.get(likesRef)

            if(likeDoc.exists()){
                transaction.delete(likesRef)
                transaction.update(commentRef, "likes", FieldValue.increment(-1))
            }else{
                transaction.set(likesRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(commentRef, "likes", FieldValue.increment(1))
            }
        }.await()
    }
}
