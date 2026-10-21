package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.CommentDto
import com.example.knowhub.data.datasource.dtos.CreateCommentDto

interface CommentRemoteDataSource {
    suspend fun createComment(comment: CreateCommentDto): Unit
    suspend fun getCommentsByReview(reviewId: String, currentUserId: String = ""): List<CommentDto>
    suspend fun getCommentById(id: String, currentUserId: String = ""): CommentDto
    suspend fun getReplies(commentId: String, currentUserId: String = ""): List<CommentDto>
    suspend fun deleteComment(id: String): Unit
    suspend fun deleteCommentsByReview(reviewId: String): Unit

    suspend fun sendOrDeleteCommentLike(commentId: String, userId: String): Unit
}
