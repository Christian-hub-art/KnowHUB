package com.example.knowhub.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.NotificationRepository
import com.example.knowhub.data.datasource.services.NotificationTokenStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationsState())
    val uiState: StateFlow<NotificationsState> = _uiState.asStateFlow()

    init {
        NotificationTokenStore.registerCurrentToken()
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            notificationRepository.listenNotifications()
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "No se pudieron cargar las notificaciones."
                        )
                    }
                }
                .collect { notifications ->
                    _uiState.update {
                        it.copy(
                            notifications = notifications,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }
}
