package com.example.knowhub.data

data class Review(
    val id: String,
    val idAsignatura: String,
    val idUsuario: String,
    val nombreEstudiante: String,
    val nombreProfesor: String,
    val nombreAsignatura: String,
    val descripcion: String,
    val fechaPublicacion: String,
    val calificacion: String,
    val likes: String,
    val cantidadComentarios: String,
    val parentReviewId: String
)
