package com.example.knowhub.data.datasource.services

import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewRatingDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.Query

interface ReviewRetrofitService {
    @POST("reviews")
    suspend fun createReview(@Body review: CreateReviewDto)

    @GET("reviews")
    suspend fun getAllReviews(
        @Query("currentUserId") currentUserId: String = ""
    ): List<ReviewDto>

    @GET("users/{userId}/reviews")
    suspend fun getReviewsByUser(
        @Path("userId") userId: String,
        @Query("currentUserId") currentUserId: String = ""
    ): List<ReviewDto>

    @GET("reviews/{id}")
    suspend fun getReviewById(
        @Path("id") id: String,
        @Query("currentUserId") currentUserId: String = ""
    ): ReviewDto

    @PATCH("reviews/{id}")
    suspend fun updateReview(
        @Path("id") id: String,
        @Body review: CreateReviewDto
    )

    @DELETE("reviews/{id}")
    suspend fun deleteReview(@Path("id") id: String)

    @PUT("reviews/{reviewId}/ratings/{userId}")
    suspend fun rateReview(
        @Path("reviewId") reviewId: String,
        @Path("userId") userId: String,
        @Body rating: ReviewRatingDto
    )
}
