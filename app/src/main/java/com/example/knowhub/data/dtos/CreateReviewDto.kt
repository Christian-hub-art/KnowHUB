package com.example.knowhub.data.dtos

data class CreateReviewUserDto(
    val name: String? = null,
    val username: String? = null,
    val profileImage: String? = null
)

data class CreateReviewDto(
    val idAsignatura: String? = null,
    var idUsuario: String? = null,
    val nombreEstudiante: String? = null,
    val nombreProfesor: String? = null,
    val nombreAsignatura: String? = null,
    val descripcion: String,
    val calificacion: Int = 0,
    val parentReviewId: String? = null,
    val reviewId: String? = null,
    var user: CreateReviewUserDto? = null
)
