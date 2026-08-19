package com.example.knowhub.data

import java.util.Date

data class Like(
    val idLike: Int,
    val fecha: Date,

    // Relación * a 1 con Usuario
    val usuario: Usuario,

    // Relación * a 1 con Review (opcional)
    val review: Review? = null,

    // Relación * a 1 con Comentario (opcional)
    val comentario: Comentario? = null
)
