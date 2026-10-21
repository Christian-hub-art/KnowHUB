package com.example.knowhub.data.datasource.repository

import com.example.knowhub.data.Notificacion
import com.example.knowhub.data.datasource.AuthRemoteDataSource
import com.example.knowhub.data.datasource.NotificationRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationRemoteDataSource: NotificationRemoteDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    fun listenNotifications(): Flow<List<Notificacion>> =
        notificationRemoteDataSource.listenNotifications(
            authRemoteDataSource.currentUser?.uid.orEmpty()
        )
}
