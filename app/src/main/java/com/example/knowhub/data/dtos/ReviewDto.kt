package com.example.knowhub.data.dtos

import com.example.knowhub.data.Review

data class ReviewDto(
    val id: Int,
    val idAsignatura: Int,
    val idUsuario: Int,
    val nombreEstudiante: String,
    val nombreProfesor: String,
    val nombreAsignatura: String,
    val descripcion: String,
    val fechaPublicacion: String,
    val calificacion: Int,
    val likes: Int,
    val cantidadComentarios: Int,
    val parentReviewId: Int?,
    val user: UserProfileDto?
)

fun ReviewDto.toReview(): Review {
    return Review(
        id = id.toString(),
        idAsignatura = idAsignatura.toString(),
        idUsuario = idUsuario.toString(),
        nombreEstudiante = nombreEstudiante,
        nombreProfesor = nombreProfesor,
        nombreAsignatura = nombreAsignatura,
        descripcion = descripcion,
        fechaPublicacion = fechaPublicacion,
        calificacion = calificacion.toString(),
        likes = likes.toString(),
        cantidadComentarios = cantidadComentarios.toString(),
        parentReviewId = parentReviewId?.toString() ?: ""
    )
}
