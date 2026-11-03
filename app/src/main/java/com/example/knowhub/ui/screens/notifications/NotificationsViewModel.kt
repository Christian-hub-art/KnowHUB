package com.example.knowhub.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.NotificacionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//ViewModel encargado de la lógica de negocio y gestión de notificaciones.
@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificacionRepository: NotificacionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState())
    val uiState: StateFlow<NotificationsState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }
    //Escucha en tiempo real las notificaciones del usuario guardadas en Firestore.
    private fun loadNotifications() {
        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(errorMessage = "Inicia sesión para ver tus notificaciones") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            notificacionRepository.getNotificacionesLive(userId)
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "No se pudieron cargar las notificaciones"
                        )
                    }
                }
                .collect { notificaciones ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            notifications = notificaciones,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }
}
