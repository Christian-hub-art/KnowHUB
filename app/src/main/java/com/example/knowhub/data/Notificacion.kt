package com.example.knowhub.data

import java.util.Date

data class Notificacion(
    val tipo: String,
    val titulo: String,
    val descripcion: String,
    val id: String = "",
    val actorId: String = "",
    val reviewId: String = "",
    val commentId: String = "",
    val createdAt: Long = 0L,
    val leida: Boolean = false
)
