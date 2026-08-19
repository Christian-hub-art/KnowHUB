package com.example.knowhub.data

import java.util.Date

data class Review(
    val idReview: Int,
    val descripcion: String,
    val fechaPublicacion: Date,
    val calificacion: Int,
    val dificultad: Int,
    val recomendada: Boolean,

    // Relación * a 1 con Asignatura
    val asignatura: Asignatura,

    // Relación * a 1 con Usuario
    val usuario: Usuario,

    // Relación 1 a * con Like
    val likes: List<Like> = emptyList(),

    // Relación 1 a * con Comentario
    val comentarios: List<Comentario> = emptyList()
)
