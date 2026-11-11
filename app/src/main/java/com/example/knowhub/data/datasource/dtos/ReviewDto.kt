package com.example.knowhub.data.datasource.dtos

import com.example.knowhub.data.Review
import com.google.firebase.Timestamp

// Datos del autor copiados dentro de la reseña
data class ReviewUserDto(
    val name: String = "",
    val userName: String = "",
    val profileImage: String? = null
)

data class ReviewDto(
    val id: String = "",              // id del documento en Firestore
    val userId: String = "",          // uid del autor
    val clase: String = "",
    val nombreAsignatura: String = "",
    val nombreProfesor: String = "",
    val descripcion: String = "",
    val calificacion: Int = 0,
    val likes: Int = 0,
    val cantidadComentarios: Int = 0,
    val fechaPublicacion: String = "",
    val user: ReviewUserDto = ReviewUserDto(),
    var liked: Boolean = false,
    val generalReviewId: Int = 0,
    // Campos de ubicación y fecha que Firestore devuelve para mostrar el mapa.
    val latitude: Double? = null,
    val longitude: Double? = null,
    val publishedAt: Timestamp? = null
)

data class CreateReviewDto(
    val userId: String = "",
    val clase: String = "",
    val nombreAsignatura: String = "",
    val nombreProfesor: String = "",
    val descripcion: String = "",
    val user: ReviewUserDto = ReviewUserDto(),
    val generalReviewId: Int = 0,
    val calificacion: Int = 0,
    // Se envían al crear la reseña solo si la persona comparte su ubicación.
    val latitude: Double? = null,
    val longitude: Double? = null
)

fun ReviewDto.toReview(): Review {
    return Review(
        id = id,
        nombreEstudiante = user.userName,
        nombreProfesor = nombreProfesor,
        nombreAsignatura = nombreAsignatura,
        descripcion = descripcion,
        fechaPublicacion = fechaPublicacion,
        calificacion = calificacion,
        likes = likes,
        cantidadComentarios = cantidadComentarios,
        clase = clase,
        fotoAutor = user.profileImage,
        userId = userId,
        liked = liked,
        generalReviewId = generalReviewId,
        latitude = latitude,
        longitude = longitude,
        publishedAt = publishedAt
    )
}
