package com.example.knowhub.data.datasource.services

import com.example.knowhub.data.Asignatura
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AsignaturaRetrofitService {

    @GET("asignatura")
    suspend fun getAsignatura(): List<Asignatura>

    @GET("asignatura/{id}")
    suspend fun getAsignaturaById(@Path("id") id: String): Asignatura

    @POST("asignatura")
    suspend fun createAsignatura(@Body asignatura: Asignatura)

    @PUT("asignatura/{id}")
    suspend fun updateAsignatura(@Path("id") id: String, @Body asignatura: Asignatura)

    @DELETE("asignatura/{id}")
    suspend fun deleteAsignatura(@Path("id") id: String)
}
