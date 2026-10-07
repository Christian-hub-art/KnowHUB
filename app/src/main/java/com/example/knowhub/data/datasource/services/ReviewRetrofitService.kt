package com.example.knowhub.data.datasource.services

import com.example.knowhub.data.dtos.CreateReviewDto
import com.example.knowhub.data.dtos.ReviewDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewRetrofitService {

    @GET("review")
    suspend fun getAllReviews(): List<ReviewDto>

    @POST("review")
    suspend fun createReview(@Body review: CreateReviewDto)

    @DELETE("review/{id}")
    suspend fun deleteReview(@Path("id") id: String)

    @PUT("review/{id}")
    suspend fun updateReview(@Path("id") id: String, @Body review: CreateReviewDto)

    @GET("review/{id}")
    suspend fun getReviewById(@Path("id") id: String): ReviewDto

    @GET("review/{id}/replies")
    suspend fun getReviewReplies(@Path("id") id: String): List<ReviewDto>

    @GET("asignatura/{id}/reviews")
    suspend fun getReviewsByAsignatura(@Path("id") asignaturaId: String): List<ReviewDto>

    @GET("usuario/{id}/reviews")
    suspend fun getReviewsByUsuario(@Path("id") usuarioId: String): List<ReviewDto>
}
