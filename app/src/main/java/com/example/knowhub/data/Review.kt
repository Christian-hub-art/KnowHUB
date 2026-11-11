package com.example.knowhub.data

import com.google.firebase.Timestamp

data class Review(
    val id: String,
    val nombreEstudiante: String,
    val nombreProfesor: String,
    val nombreAsignatura: String,
    val descripcion: String,
    val fechaPublicacion: String,
    val calificacion: Int,
    val likes: Int,
    val cantidadComentarios: Int,
    val clase: String = "",
    val fotoAutor: String? = null,
    val userId: String = "",
    val liked: Boolean = false,
    val generalReviewId: Int = 0,
    // Coordenadas y fecha del servidor usadas para ubicar la reseña en el mapa reciente.
    val latitude: Double? = null,
    val longitude: Double? = null,
    val publishedAt: Timestamp? = null
)
