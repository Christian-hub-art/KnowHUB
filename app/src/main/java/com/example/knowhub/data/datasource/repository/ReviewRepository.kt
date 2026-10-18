package com.example.knowhub.data.repository

import android.util.Log
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.knowhub.data.dtos.CreateReviewDto
import com.example.knowhub.data.dtos.ReviewDto
import com.example.knowhub.data.dtos.ReviewUserDto
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewFirestoreDataSourceImpl,
    private val userRemoteDataSource: UserFirestoreDataSourceImpl,
    private val authRemoteDataSource: AuthRemoteDataSource
) {

    suspend fun getReviews(): Result<List<ReviewDto>> {
        return try {
            val reviews = reviewRemoteDataSource.getAllReviews()
            Result.success(reviews)
        } catch (e: Exception) {
            Log.d("TAG", "getReviews: ${e.message}")
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
}