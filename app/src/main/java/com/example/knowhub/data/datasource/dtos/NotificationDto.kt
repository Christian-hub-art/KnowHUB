package com.example.knowhub.data.datasource.dtos

import com.example.knowhub.data.Notificacion
import com.google.firebase.Timestamp

data class NotificationDto(
    val type: String = "",
    val title: String = "",
    val body: String = "",
    val actorId: String = "",
    val reviewId: String = "",
    val commentId: String = "",
    val read: Boolean = false,
    val createdAt: Timestamp? = null
) {
    fun toNotification(id: String) = Notificacion(
        tipo = type,
        titulo = title,
        descripcion = body,
        id = id,
        actorId = actorId,
        reviewId = reviewId,
        commentId = commentId,
        createdAt = createdAt?.toDate()?.time ?: 0L,
        leida = read
    )
}
