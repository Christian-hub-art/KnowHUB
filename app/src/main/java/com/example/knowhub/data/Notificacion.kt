package com.example.knowhub.data

import java.util.Date

data class Notificacion(
    val tipo: String,
    val titulo: String,
    val descripcion: String,
    val reviewId: String = "",     // reseña a abrir (like y comentario)
    val userId: String = ""        // perfil a abrir (nuevo seguidor)
)
