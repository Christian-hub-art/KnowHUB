package com.example.knowhub.data.repository

import coil.network.HttpException
import com.example.knowhub.data.Review
import com.example.knowhub.data.datasource.ReviewRemoteDataSource
import com.example.knowhub.data.dtos.CreateReviewDto
import com.example.knowhub.data.dtos.CreateReviewUserDto
import com.example.knowhub.data.dtos.toReview
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewRemoteDataSource
) {
    suspend fun getReviews(): Result<List<Review>> {
        return try {
            val reviews = reviewRemoteDataSource.getAllReviews()
            val reviewsInfo = reviews.map { it.toReview() }
            Result.success(reviewsInfo)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createReview(
        idAsignatura: String,
        idUsuario: String,
        descripcion: String,
        calificacion: Int,
        nombreProfesor: String = "",
        nombreAsignatura: String = "",
        nombreEstudiante: String = "",
        parentReviewId: String? = null
    ): Result<Unit> {
        return try {
            val createReviewUserDto = CreateReviewUserDto(
                name = nombreEstudiante
            )
            val createReviewDto = CreateReviewDto(
                idAsignatura = idAsignatura,
                idUsuario = idUsuario,
                nombreEstudiante = nombreEstudiante,
                nombreProfesor = nombreProfesor,
                nombreAsignatura = nombreAsignatura,
                descripcion = descripcion,
                calificacion = calificacion,
                parentReviewId = parentReviewId,
                user = createReviewUserDto
            )
            reviewRemoteDataSource.createReview(createReviewDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateReview(
        reviewId: String,
        idAsignatura: String,
        idUsuario: String,
        descripcion: String,
        calificacion: Int,
        nombreProfesor: String = "",
        nombreAsignatura: String = "",
        nombreEstudiante: String = "",
        parentReviewId: String? = null
    ): Result<Unit> {
        return try {
            val createReviewUserDto = CreateReviewUserDto(
                name = nombreEstudiante
            )
            val updateReviewDto = CreateReviewDto(
                idAsignatura = idAsignatura,
                idUsuario = idUsuario,
                nombreEstudiante = nombreEstudiante,
                nombreProfesor = nombreProfesor,
                nombreAsignatura = nombreAsignatura,
                descripcion = descripcion,
                calificacion = calificacion,
                parentReviewId = parentReviewId,
                reviewId = reviewId,
                user = createReviewUserDto
            )
            reviewRemoteDataSource.updateReview(reviewId, updateReviewDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteReview(id: String): Result<Unit> {
        return try {
            reviewRemoteDataSource.deleteReview(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewById(id: String): Result<Review> {
        return try {
            val reviewDto = reviewRemoteDataSource.getReviewById(id)
            Result.success(reviewDto.toReview())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByAsignatura(asignaturaId: String): Result<List<Review>> {
        return try {
            val reviews = reviewRemoteDataSource.getReviewsByAsignatura(asignaturaId)
            val reviewsInfo = reviews.map { it.toReview() }
            Result.success(reviewsInfo)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByUsuario(usuarioId: String): Result<List<Review>> {
        return try {
            val reviews = reviewRemoteDataSource.getReviewsByUsuario(usuarioId)
            val reviewsInfo = reviews.map { it.toReview() }
            Result.success(reviewsInfo)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewReplies(id: String): Result<List<Review>> {
        return try {
            val reviews = reviewRemoteDataSource.getReviewReplies(id)
            val reviewsInfo = reviews.map { it.toReview() }
            Result.success(reviewsInfo)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
