package com.example.knowhub.data

import java.util.Date

data class Comentario(
    val idComentario: Int,
    val fechaPublicacion: Date,
    val descripcion: String,

    // Relación * a 1 con Review
    val review: Review,

    // Relación * a 1 con Usuario
    val usuario: Usuario,

    // Relación 1 a * con Like
    val likes: List<Like> = emptyList(),

    // Relación reflexiva
    val comentarioPadre: Comentario? = null,
    val respuestas: List<Comentario> = emptyList()
)
