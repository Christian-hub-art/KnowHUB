package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.GeneralReviewDto

interface GeneralReviewRemoteDataSource {
    suspend fun getAllGeneralReviews(): List<GeneralReviewDto>
    suspend fun getGeneralReviewById(id: Int): GeneralReviewDto?
    suspend fun getGeneralReviewByCodigo(codigoAsignatura: Int): GeneralReviewDto?
}
