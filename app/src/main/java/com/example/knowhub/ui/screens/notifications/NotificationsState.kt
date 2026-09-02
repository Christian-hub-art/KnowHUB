package com.example.knowhub.ui.screens.notifications

import com.example.knowhub.data.Notificacion
//Estado que representa la interfaz de notificaciones.
data class NotificationsState(
    val notifications: List<Notificacion> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)