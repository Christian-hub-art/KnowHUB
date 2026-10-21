package com.example.knowhub.data.datasource.services

import com.example.knowhub.data.datasource.dtos.CommentDto
import com.example.knowhub.data.datasource.dtos.CreateCommentDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentRetrofitService {
    @POST("comments")
    suspend fun createComment(@Body comment: CreateCommentDto)

    @GET("reviews/{reviewId}/comments")
    suspend fun getCommentsByReview(
        @Path("reviewId") reviewId: String,
        @Query("currentUserId") currentUserId: String = ""
    ): List<CommentDto>

    @GET("comments/{id}")
    suspend fun getCommentById(
        @Path("id") id: String,
        @Query("currentUserId") currentUserId: String = ""
    ): CommentDto

    @GET("comments/{commentId}/replies")
    suspend fun getReplies(
        @Path("commentId") commentId: String,
        @Query("currentUserId") currentUserId: String = ""
    ): List<CommentDto>

    @POST("comments/{commentId}/likes/{userId}")
    suspend fun toggleLike(
        @Path("commentId") commentId: String,
        @Path("userId") userId: String
    )

    @DELETE("comments/{id}")
    suspend fun deleteComment(@Path("id") id: String)

    @DELETE("reviews/{reviewId}/comments")
    suspend fun deleteCommentsByReview(@Path("reviewId") reviewId: String)
}
