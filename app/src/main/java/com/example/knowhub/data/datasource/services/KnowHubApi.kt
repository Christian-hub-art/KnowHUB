package com.example.knowhub.data.datasource.services

import com.example.knowhub.data.dto.AsignaturaDto
import com.example.knowhub.data.dto.ReviewDto
import retrofit2.http.GET
import retrofit2.http.Path

interface KnowHubApi {
    @GET("review") suspend fun reviews(): List<ReviewDto>
    @GET("asignatura") suspend fun asignaturas(): List<AsignaturaDto>
    @GET("asignatura/{id}") suspend fun asignatura(@Path("id") id: Int): AsignaturaDto
    @GET("asignatura/{id}/reviews") suspend fun reviewsDeAsignatura(@Path("id") id: Int): List<ReviewDto>
    @GET("review/{id}") suspend fun review(@Path("id") id: Int): ReviewDto
    @GET("review/{id}/replies") suspend fun respuestas(@Path("id") id: Int): List<ReviewDto>
}


