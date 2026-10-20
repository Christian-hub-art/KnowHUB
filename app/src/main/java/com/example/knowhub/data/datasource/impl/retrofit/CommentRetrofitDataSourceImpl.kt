package com.example.knowhub.data.datasource.impl.retrofit

import com.example.knowhub.data.datasource.CommentRemoteDataSource
import com.example.knowhub.data.datasource.dtos.CommentDto
import com.example.knowhub.data.datasource.dtos.CreateCommentDto
import com.example.knowhub.data.datasource.services.CommentRetrofitService
import javax.inject.Inject

class CommentRetrofitDataSourceImpl @Inject constructor(
    private val service: CommentRetrofitService
) : CommentRemoteDataSource {
    override suspend fun createComment(comment: CreateCommentDto) {
        service.createComment(comment)
    }

    override suspend fun getCommentsByReview(
        reviewId: String,
        currentUserId: String
    ): List<CommentDto> = service.getCommentsByReview(reviewId, currentUserId)

    override suspend fun getCommentById(id: String, currentUserId: String): CommentDto =
        service.getCommentById(id, currentUserId)

    override suspend fun getReplies(commentId: String, currentUserId: String): List<CommentDto> =
        service.getReplies(commentId, currentUserId)

    override suspend fun toggleLike(commentId: String, userId: String) {
        service.toggleLike(commentId, userId)
    }

    override suspend fun deleteComment(id: String) {
        service.deleteComment(id)
    }

    override suspend fun deleteCommentsByReview(reviewId: String) {
        service.deleteCommentsByReview(reviewId)
    }
}
