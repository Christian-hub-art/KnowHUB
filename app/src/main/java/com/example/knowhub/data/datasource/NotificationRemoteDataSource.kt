package com.example.knowhub.data.datasource

import com.example.knowhub.data.Notificacion
import kotlinx.coroutines.flow.Flow

interface NotificationRemoteDataSource {
    fun listenNotifications(userId: String): Flow<List<Notificacion>>
}
