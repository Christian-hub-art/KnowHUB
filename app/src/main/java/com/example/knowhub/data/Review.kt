package com.example.knowhub.data

import java.util.Date

data class Review(
    val id: Int,
    val nombreEstudiante: String,
    val nombreProfesor: String,
    val nombreAsignatura: String,
    val descripcion: String,
    val fechaPublicacion: String,
    val calificacion: Int,
    val likes: Int,
    val cantidadComentarios: Int,
)
