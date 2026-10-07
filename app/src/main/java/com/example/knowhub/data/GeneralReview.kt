package com.example.knowhub.data

import java.util.Date

data class GeneralReview(
    val id: String,
    val nombreMateria: String,
    val nombreProfesor: String,
    val codigoAsignatura: String,
    val cantidadReviews: Int,
    val calificacionMedia: Int,
    val dificultadMedia: String,
    val Hashtags: List<String>,
)





