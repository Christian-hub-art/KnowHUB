package com.example.knowhub.data.datasource.repository

import android.util.Log
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.datasource.dtos.toGeneralReview
import com.example.knowhub.data.datasource.impl.firestore.GeneralReviewFirestoreDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GeneralReviewRepository @Inject constructor(
    private val generalReviewRemoteDataSource: GeneralReviewFirestoreDataSourceImpl
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
}
