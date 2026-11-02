package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.GeneralReviewDto
import kotlinx.coroutines.flow.Flow

interface GeneralReviewRemoteDataSource {
    suspend fun getAllGeneralReviews(): List<GeneralReviewDto>
    suspend fun getGeneralReviewById(id: Int): GeneralReviewDto?
    suspend fun getGeneralReviewByCodigo(codigoAsignatura: Int): GeneralReviewDto?
    fun listenAllGeneralReviews(): Flow<List<GeneralReviewDto>>
}
