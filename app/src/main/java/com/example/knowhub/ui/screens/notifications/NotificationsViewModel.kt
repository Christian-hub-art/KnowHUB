package com.example.knowhub.ui.screens.notifications

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.LocalNotificacionProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
//ViewModel encargado de la lógica de negocio y gestión de notificaciones.
class NotificationsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState())
    val uiState: StateFlow<NotificationsState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }
    //Carga el listado inicial de notificaciones desde la fuente de datos local.
    private fun loadNotifications() {
        val list = LocalNotificacionProvider.notificaciones
        _uiState.update { currentState ->
            currentState.copy(
                notifications = list,
                isLoading = false
            )
        }
    }
    //Elimina una notificación en el índice especificado de la lista actual.
    fun deleteNotification(index: Int) {
        _uiState.update { currentState ->
            val updatedList = currentState.notifications.toMutableList().apply {
                if (index in indices) removeAt(index)
            }
            currentState.copy(notifications = updatedList)
        }
    }
}