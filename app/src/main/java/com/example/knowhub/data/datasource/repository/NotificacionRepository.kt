package com.example.knowhub.data.datasource.repository

import com.example.knowhub.data.Notificacion
import com.example.knowhub.data.datasource.dtos.toNotificacion
import com.example.knowhub.data.datasource.impl.firestore.NotificacionFirestoreDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificacionRepository @Inject constructor(
    private val notificacionRemoteDataSource: NotificacionFirestoreDataSourceImpl
) {

    fun getNotificacionesLive(userId: String): Flow<List<Notificacion>> {
        return notificacionRemoteDataSource.listenNotificaciones(userId).map { notificaciones ->
            notificaciones.map { it.toNotificacion() }
        }
    }
}
