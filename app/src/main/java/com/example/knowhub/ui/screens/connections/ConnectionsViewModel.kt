package com.example.knowhub.ui.screens.connections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ConnectionsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ConnectionsState())
    val uiState: StateFlow<ConnectionsState> = _uiState

    fun loadData(userId: String, followers: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeError = false) }
            val result = if (followers) {
                userRepository.getFollowers(userId)
            } else {
                userRepository.getFollowing(userId)
            }
            if (result.isSuccess) {
                _uiState.update { it.copy(users = result.getOrNull() ?: emptyList(), isLoading = false) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar la lista"
                    )
                }
            }
        }
    }
}
