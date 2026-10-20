package com.example.knowhub.data.datasource.impl.firestore

import com.example.knowhub.data.datasource.CommentRemoteDataSource
import com.example.knowhub.data.datasource.dtos.CommentDto
import com.example.knowhub.data.datasource.dtos.CreateCommentDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommentFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CommentRemoteDataSource {

    override suspend fun createComment(comment: CreateCommentDto) {
        require(comment.userId.isNotBlank()) { "Se requiere un usuario autenticado." }
        val commentRef = db.collection("comments").document()
        val reviewRef = db.collection("reviews").document(comment.reviewId)
        val parentRef = comment.parentCommentId?.let { db.collection("comments").document(it) }

        db.runTransaction { transaction ->
            val review = transaction.get(reviewRef)
            val parent = parentRef?.let { transaction.get(it) }
            check(review.exists()) { "La reseña ya no existe." }
            if (parentRef != null) check(parent?.exists() == true) { "El comentario padre ya no existe." }

            val recipientId = if (parentRef != null) {
                parent?.getString("userId").orEmpty()
            } else {
                review.getString("userId").orEmpty()
            }
            val notificationRef = if (recipientId.isNotBlank() && recipientId != comment.userId) {
                db.collection("users").document(recipientId).collection("notifications").document()
            } else {
                null
            }
            val commentData = mapOf(
                "reviewId" to comment.reviewId,
                "parentCommentId" to comment.parentCommentId,
                "userId" to comment.userId,
                "comentario" to comment.comentario,
                "fecha" to comment.fecha,
                "user" to comment.user
            )
            transaction.set(commentRef, commentData)
            if (parentRef != null) {
                transaction.update(parentRef, "cantidadComentarios", FieldValue.increment(1))
            } else {
                transaction.update(reviewRef, "cantidadComentarios", FieldValue.increment(1))
            }
            if (notificationRef != null) {
                val actorName = comment.user?.userName?.ifBlank { "Un usuario" } ?: "Un usuario"
                transaction.set(
                    notificationRef,
                    mapOf(
                        "type" to "comment",
                        "title" to "Nuevo comentario",
                        "body" to "$actorName comentó en tu publicación.",
                        "actorId" to comment.userId,
                        "reviewId" to comment.reviewId,
                        "commentId" to commentRef.id,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "read" to false
                    )
                )
            }
        }
        .await()
    }

    // Respuestas directas a la reseña. Se filtra en código porque los comentarios
    // antiguos no tienen el campo parentCommentId y Firestore no los encontraría con == null.
    override suspend fun getCommentsByReview(
        reviewId: String,
        currentUserId: String
    ): List<CommentDto> {
        val snapshot = db.collection("comments").whereEqualTo("reviewId", reviewId).get().await()
        val comments = snapshot.documents.mapNotNull { doc ->
            doc.toObject(CommentDto::class.java)?.copy(id = doc.id)
        }.filter { it.parentCommentId == null }
        return comments.withCurrentUserLikes(currentUserId)
    }

    override suspend fun getCommentById(id: String, currentUserId: String): CommentDto {
        val doc = db.collection("comments").document(id).get().await()
        val comment = doc.toObject(CommentDto::class.java)?.copy(id = doc.id)
            ?: throw NoSuchElementException("Comentario no encontrado")
        return comment.withCurrentUserLike(currentUserId)
    }

    override suspend fun getReplies(commentId: String, currentUserId: String): List<CommentDto> {
        val snapshot = db.collection("comments").whereEqualTo("parentCommentId", commentId).get().await()
        val replies = snapshot.documents.mapNotNull { doc ->
            doc.toObject(CommentDto::class.java)?.copy(id = doc.id)
        }
        return replies.withCurrentUserLikes(currentUserId)
    }

    override suspend fun toggleLike(commentId: String, userId: String) {
        require(userId.isNotBlank()) { "Inicia sesión para dar me gusta a un comentario." }

        val commentRef = db.collection("comments").document(commentId)
        val likeRef = commentRef.collection("likes").document(userId)
        val userRef = db.collection("users").document(userId)
        db.runTransaction { transaction ->
            val comment = transaction.get(commentRef)
            val like = transaction.get(likeRef)
            check(comment.exists()) { "El comentario ya no existe." }
            check(comment.getString("userId") != userId) {
                "No puedes dar me gusta a tu propio comentario."
            }

            val currentLikes = comment.getLong("likes")?.toInt() ?: 0
            if (like.exists()) {
                transaction.delete(likeRef)
                transaction.update(commentRef, "likes", (currentLikes - 1).coerceAtLeast(0))
            } else {
                val user = transaction.get(userRef)
                check(user.exists()) { "No se encontró el perfil del usuario." }
                val likerName = user.getString("name")
                    ?.takeIf { it.isNotBlank() }
                    ?: user.getString("userName")?.takeIf { it.isNotBlank() }
                    ?: throw IllegalStateException("El perfil del usuario no tiene nombre.")
                transaction.set(
                    likeRef,
                    mapOf("nombre" to likerName)
                )
                transaction.update(commentRef, "likes", currentLikes + 1)
                val ownerId = comment.getString("userId").orEmpty()
                if (ownerId.isNotBlank()) {
                    val notificationRef = db.collection("users")
                        .document(ownerId)
                        .collection("notifications")
                        .document()
                    transaction.set(
                        notificationRef,
                        mapOf(
                            "type" to "like",
                            "title" to "Me gusta en tu comentario",
                            "body" to "$likerName indicó que le gusta tu comentario.",
                            "actorId" to userId,
                            "commentId" to commentId,
                            "reviewId" to comment.getString("reviewId").orEmpty(),
                            "createdAt" to FieldValue.serverTimestamp(),
                            "read" to false
                        )
                    )
                }
            }
        }.await()
    }

    override suspend fun deleteComment(id: String) {
        val comment = getCommentById(id, "")
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
        val commentRef = db.collection("comments").document(id)
        val likes = commentRef.collection("likes").get().await()
        likes.documents.chunked(500).forEach { documents ->
            db.runBatch { batch ->
                documents.forEach { document -> batch.delete(document.reference) }
            }.await()
        }
        commentRef.delete().await()

        val replies = db.collection("comments").whereEqualTo("parentCommentId", id).get().await()
        for (doc in replies.documents) {
            deleteThread(doc.id)
        }
    }

    override suspend fun deleteCommentsByReview(reviewId: String) {
        val replies = getCommentsByReview(reviewId, "")
        for (reply in replies) {
            deleteThread(reply.id)
        }
    }

    private suspend fun List<CommentDto>.withCurrentUserLikes(userId: String): List<CommentDto> =
        coroutineScope {
            map { comment -> async { comment.withCurrentUserLike(userId) } }.awaitAll()
        }

    private suspend fun CommentDto.withCurrentUserLike(userId: String): CommentDto {
        if (userId.isBlank()) return this
        val liked = db.collection("comments")
            .document(id)
            .collection("likes")
            .document(userId)
            .get()
            .await()
            .exists()
        return copy(likedByCurrentUser = liked)
    }
}
