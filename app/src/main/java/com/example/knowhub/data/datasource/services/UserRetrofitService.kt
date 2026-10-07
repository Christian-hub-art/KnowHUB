package com.example.knowhub.data.datasource.services

import com.example.knowhub.data.Usuario
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserRetrofitService {

    @GET("usuario")
    suspend fun getUsuario(): List<Usuario>

    @GET("usuario/{id}")
    suspend fun getUsuarioById(@Path("id") id: String): Usuario

    @POST("usuario")
    suspend fun createUsuario(@Body usuario: Usuario)

    @PUT("usuario/{id}")
    suspend fun updateUsuario(@Path("id") id: String, @Body usuario: Usuario)

    @DELETE("usuario/{id}")
    suspend fun deleteUsuario(@Path("id") id: String)
}
