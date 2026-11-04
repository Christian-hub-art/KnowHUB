package com.example.knowhub.data.datasource.repository

import android.util.Log
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.datasource.dtos.CreateGeneralReviewDto
import com.example.knowhub.data.datasource.dtos.toGeneralReview
import com.example.knowhub.data.datasource.impl.firestore.CommentFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.GeneralReviewFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GeneralReviewRepository @Inject constructor(
    private val generalReviewRemoteDataSource: GeneralReviewFirestoreDataSourceImpl,
    private val reviewRemoteDataSource: ReviewFirestoreDataSourceImpl,
    private val commentRemoteDataSource: CommentFirestoreDataSourceImpl
) {

    suspend fun getGeneralReviews(): Result<List<GeneralReview>> {
        return try {
            val generalReviews = generalReviewRemoteDataSource.getAllGeneralReviews()
            Result.success(generalReviews.map { it.toGeneralReview() })
        } catch (e: Exception) {
            Log.d("TAG", "getGeneralReviews: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getGeneralReviewById(id: Int): Result<GeneralReview> {
        return try {
            val generalReview = generalReviewRemoteDataSource.getGeneralReviewById(id)
            if (generalReview == null) return Result.failure(NoSuchElementException("Materia $id no encontrada"))
            Result.success(generalReview.toGeneralReview())
        } catch (e: Exception) {
            Log.d("TAG", "getGeneralReviewById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getGeneralReviewByCodigo(codigoAsignatura: Int): Result<GeneralReview> {
        return try {
            val generalReview = generalReviewRemoteDataSource.getGeneralReviewByCodigo(codigoAsignatura)
            if (generalReview == null) return Result.failure(NoSuchElementException("Materia con código $codigoAsignatura no encontrada"))
            Result.success(generalReview.toGeneralReview())
        } catch (e: Exception) {
            Log.d("TAG", "getGeneralReviewByCodigo: ${e.message}")
            Result.failure(e)
        }
    }

    fun getGeneralReviewsLive(): Flow<List<GeneralReview>> {
        return generalReviewRemoteDataSource.listenAllGeneralReviews().map { materias ->
            materias.map { it.toGeneralReview() }
        }
    }

    suspend fun createGeneralReview(
        nombreMateria: String,
        nombreProfesor: String,
        codigoAsignatura: Int,
        dificultadMedia: String,
        hashtags: List<String>,
        userId: String = ""
    ): Result<Unit> {
        return try {
            val createGeneralReviewDto = CreateGeneralReviewDto(
                id = codigoAsignatura,
                nombreMateria = nombreMateria,
                nombreProfesor = nombreProfesor,
                codigoAsignatura = codigoAsignatura,
                dificultadMedia = dificultadMedia,
                hashtags = hashtags,
                userId = userId
            )
            generalReviewRemoteDataSource.createGeneralReview(createGeneralReviewDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "createGeneralReview: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateGeneralReview(
        id: Int,
        nombreMateria: String,
        nombreProfesor: String,
        dificultadMedia: String,
        hashtags: List<String>
    ): Result<Unit> {
        return try {
            val createGeneralReviewDto = CreateGeneralReviewDto(
                nombreMateria = nombreMateria,
                nombreProfesor = nombreProfesor,
                dificultadMedia = dificultadMedia,
                hashtags = hashtags
            )
            generalReviewRemoteDataSource.updateGeneralReview(id, createGeneralReviewDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "updateGeneralReview: ${e.message}")
            Result.failure(e)
        }
    }

    // Borra la materia junto con sus reseñas y los comentarios de cada reseña
    suspend fun deleteGeneralReview(id: Int): Result<Unit> {
        return try {
            val reviews = reviewRemoteDataSource.getReviewsByGeneralReview(id)
            for (review in reviews) {
                reviewRemoteDataSource.deleteReview(review.id)
                commentRemoteDataSource.deleteCommentsByReview(review.id)
            }
            generalReviewRemoteDataSource.deleteGeneralReview(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "deleteGeneralReview: ${e.message}")
            Result.failure(e)
        }
    }
}
