package com.example.knowhub.data.datasource

import com.example.knowhub.data.dto.AsignaturaDto
import com.example.knowhub.data.dto.ReviewDto

interface CatalogRemoteDataSource {
    suspend fun asignaturas(): List<AsignaturaDto>
    suspend fun reviews(): List<ReviewDto>
    suspend fun asignatura(id: String): AsignaturaDto
    suspend fun reviewsDeAsignatura(id: String): List<ReviewDto>
    suspend fun review(id: String): ReviewDto
    suspend fun respuestas(id: String): List<ReviewDto>
}
