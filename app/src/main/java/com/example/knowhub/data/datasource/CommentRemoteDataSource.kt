package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.CommentDto
import com.example.knowhub.data.datasource.dtos.CreateCommentDto

interface CommentRemoteDataSource {
    suspend fun createComment(comment: CreateCommentDto): Unit
    suspend fun getCommentsByReview(reviewId: String): List<CommentDto>
}
