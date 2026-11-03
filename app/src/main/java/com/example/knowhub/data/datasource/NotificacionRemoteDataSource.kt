package com.example.knowhub.data.datasource

import com.example.knowhub.data.datasource.dtos.NotificacionDto
import kotlinx.coroutines.flow.Flow

interface NotificacionRemoteDataSource {
    fun listenNotificaciones(userId: String): Flow<List<NotificacionDto>>
}
