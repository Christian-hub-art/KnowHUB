package com.example.knowhub.data.datasource.impl

import com.example.knowhub.data.datasource.CatalogRemoteDataSource
import com.example.knowhub.data.datasource.services.KnowHubApi
import com.example.knowhub.data.dtos.AsignaturaDto
import com.example.knowhub.data.dtos.ReviewDto

import javax.inject.Inject

class CatalogRemoteDataSourceImpl @Inject constructor(
    private val service: KnowHubApi
) : CatalogRemoteDataSource {
    override suspend fun asignaturas(): List<AsignaturaDto> = service.asignaturas()

    override suspend fun reviews(): List<ReviewDto> = service.reviews()

    override suspend fun asignatura(id: String): AsignaturaDto = service.asignatura(id.toInt())

    override suspend fun reviewsDeAsignatura(id: String): List<ReviewDto> = service.reviewsDeAsignatura(id.toInt())

    override suspend fun review(id: String): ReviewDto = service.review(id.toInt())

    override suspend fun respuestas(id: String): List<ReviewDto> = service.respuestas(id.toInt())
}
