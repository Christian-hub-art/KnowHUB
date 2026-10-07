package com.example.knowhub.data

data class Comment(
    val id: String,
    val fecha: String,
    val estudiante: String,
    val comentario: String,
    val likes: Int,
    val cantidadComentarios: Int
)


