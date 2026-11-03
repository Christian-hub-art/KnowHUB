package com.example.knowhub.data.datasource.dtos

import com.example.knowhub.data.Notificacion
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

// Documento que guardan las Cloud Functions en users/{uid}/notificaciones
data class NotificacionDto(
    val id: String = "",
    val tipo: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: Timestamp? = null
)

fun NotificacionDto.toNotificacion(): Notificacion {
    val fechaTexto = fecha?.let {
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "CO")).format(it.toDate())
    } ?: ""
    return Notificacion(
        tipo = tipo,
        titulo = titulo,
        descripcion = if (descripcion.isNotEmpty()) "$descripcion\n$fechaTexto" else fechaTexto
    )
}
