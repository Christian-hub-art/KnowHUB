package com.example.knowhub.data.datasource.repository

import android.util.Log
import com.example.knowhub.data.Comment
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.dtos.CreateCommentDto
import com.example.knowhub.data.datasource.dtos.ReviewUserDto
import com.example.knowhub.data.datasource.dtos.toComment
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val commentRemoteDataSource: CommentFirestoreDataSourceImpl,
    private val userRemoteDataSource: UserFirestoreDataSourceImpl,
    private val authRemoteDataSource: AuthRemoteDataSource
) {

    suspend fun getCommentsByReview(reviewId: String): Result<List<Comment>> {
        val currentUserId = authRemoteDataSource.currentUser?.uid ?: ""
        return try {
            val comments = commentRemoteDataSource.getCommentsByReview(reviewId, currentUserId)
            Result.success(comments.map { it.toComment() })
        } catch (e: Exception) {
            Log.d("TAG", "getCommentsByReview: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getCommentById(id: String): Result<Comment> {
        val currentUserId = authRemoteDataSource.currentUser?.uid ?: ""
        return try {
            Result.success(commentRemoteDataSource.getCommentById(id, currentUserId).toComment())
        } catch (e: Exception) {
            Log.d("TAG", "getCommentById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getReplies(commentId: String): Result<List<Comment>> {
        val currentUserId = authRemoteDataSource.currentUser?.uid ?: ""
        return try {
            val replies = commentRemoteDataSource.getReplies(commentId, currentUserId)
            Result.success(replies.map { it.toComment() })
        } catch (e: Exception) {
            Log.d("TAG", "getReplies: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun deleteComment(id: String): Result<Unit> {
        return try {
            commentRemoteDataSource.deleteComment(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "deleteComment: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun createComment(
        reviewId: String,
        userId: String,
        comentario: String,
        parentCommentId: String? = null
    ): Result<Unit> {
        return try {
            // Desnormalización: copiamos los datos del autor dentro de la respuesta
            val user = userRemoteDataSource.getUserById(userId)
            val reviewUserDto = ReviewUserDto(
                name = user.name,
                userName = user.userName
            )
            val fecha = SimpleDateFormat("dd MMM yyyy", Locale("es", "CO")).format(Date())

            val createCommentDto = CreateCommentDto(
                reviewId = reviewId,
                parentCommentId = parentCommentId,
                userId = userId,
                comentario = comentario,
                fecha = fecha,
                user = reviewUserDto
            )
            commentRemoteDataSource.createComment(createCommentDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "createComment: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun sendOrDeleteCommentLike(commentId: String, userId: String): Result<Unit> {
        return try {
            commentRemoteDataSource.sendOrDeleteCommentLike(commentId, userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "sendOrDeleteCommentLike: ${e.message}")
            Result.failure(e)
        }
    }
}
