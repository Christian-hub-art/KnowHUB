package com.example.knowhub.data.datasource.repository

import android.util.Log
import com.example.knowhub.data.Review
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.dtos.CreateReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewDto
import com.example.knowhub.data.datasource.dtos.ReviewUserDto
import com.example.knowhub.data.datasource.dtos.toReview
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewFirestoreDataSourceImpl,
    private val userRemoteDataSource: UserFirestoreDataSourceImpl,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val commentRemoteDataSource: CommentFirestoreDataSourceImpl
) {

    suspend fun getReviews(): Result<List<Review>> {
        return try {
            val reviews = reviewRemoteDataSource.getAllReviews()
            Result.success(reviews.map { it.toReview() })
        } catch (e: Exception) {
            Log.d("TAG", "getReviews: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getReviewsByUser(userId: String): Result<List<Review>> {
        return try {
            val reviews = reviewRemoteDataSource.getReviewsByUser(userId)
            Result.success(reviews.map { it.toReview() })
        } catch (e: Exception) {
            Log.d("TAG", "getReviewsByUser: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getReviewById(id: String): Result<Review> {
        return try {
            val review = reviewRemoteDataSource.getReviewById(id)
            Result.success(review.toReview())
        } catch (e: Exception) {
            Log.d("TAG", "getReviewById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun createReview(
        userId: String,
        clase: String,
        nombreAsignatura: String,
        nombreProfesor: String,
        descripcion: String
    ): Result<Unit> {
        return try {

            val user = userRemoteDataSource.getUserById(userId)
            val photoUrl = authRemoteDataSource.currentUser?.photoUrl?.toString()

            val reviewUserDto = ReviewUserDto(
                name = user.name,
                userName = user.userName,
                profileImage = photoUrl
            )

            val createReviewDto = CreateReviewDto(
                userId = userId,
                clase = clase,
                nombreAsignatura = nombreAsignatura,
                nombreProfesor = nombreProfesor,
                descripcion = descripcion,
                user = reviewUserDto
            )

            reviewRemoteDataSource.createReview(createReviewDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "createReview: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateReview(
        reviewId: String,
        clase: String,
        nombreAsignatura: String,
        nombreProfesor: String,
        descripcion: String
    ): Result<Unit> {
        return try {
            val createReviewDto = CreateReviewDto(
                clase = clase,
                nombreAsignatura = nombreAsignatura,
                nombreProfesor = nombreProfesor,
                descripcion = descripcion
            )
            reviewRemoteDataSource.updateReview(reviewId, createReviewDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "updateReview: ${e.message}")
            Result.failure(e)
        }
    }

    // Igual que el profesor: primero borra la reseña y luego sus respuestas en cascada
    suspend fun deleteReview(reviewId: String): Result<Unit> {
        return try {
            reviewRemoteDataSource.deleteReview(reviewId)
            commentRemoteDataSource.deleteCommentsByReview(reviewId)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "deleteReview: ${e.message}")
            Result.failure(e)
        }
    }
}
