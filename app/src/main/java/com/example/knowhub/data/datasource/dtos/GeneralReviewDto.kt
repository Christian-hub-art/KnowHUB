package com.example.knowhub.data.datasource.dtos

import com.example.knowhub.data.GeneralReview

data class GeneralReviewDto(
    val id: Int = 0,
    val nombreMateria: String = "",
    val nombreProfesor: String = "",
    val codigoAsignatura: Int = 0,
    val cantidadReviews: Int = 0,
    val calificacionMedia: Int = 0,
    val sumaCalificaciones: Int = 0,
    val dificultadMedia: String = "",
    val hashtags: List<String> = emptyList(),
    val userId: String = ""
)

data class CreateGeneralReviewDto(
    val id: Int = 0,
    val nombreMateria: String = "",
    val nombreProfesor: String = "",
    val codigoAsignatura: Int = 0,
    val dificultadMedia: String = "",
    val hashtags: List<String> = emptyList(),
    val userId: String = ""
)

fun GeneralReviewDto.toGeneralReview(): GeneralReview {
    return GeneralReview(
        id = id,
        nombreMateria = nombreMateria,
        nombreProfesor = nombreProfesor,
        codigoAsignatura = codigoAsignatura,
        cantidadReviews = cantidadReviews,
        calificacionMedia = calificacionMedia,
        dificultadMedia = dificultadMedia,
        Hashtags = hashtags,
        userId = userId
    )
}
