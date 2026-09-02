package com.example.knowhub.ui.screens.notifications

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.LocalNotificacionProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotificationsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState())
    val uiState: StateFlow<NotificationsState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        val list = LocalNotificacionProvider.notificaciones
        _uiState.update { currentState ->
            currentState.copy(
                notifications = list,
                isLoading = false
            )
        }
    }

    fun deleteNotification(index: Int) {
        _uiState.update { currentState ->
            val updatedList = currentState.notifications.toMutableList().apply {
                if (index in indices) removeAt(index)
            }
            currentState.copy(notifications = updatedList)
        }
    }
}