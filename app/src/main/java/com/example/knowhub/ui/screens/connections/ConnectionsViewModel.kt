package com.example.knowhub.ui.screens.connections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ConnectionsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ConnectionsState())
    val uiState: StateFlow<ConnectionsState> = _uiState.asStateFlow()

    fun load(userId: String, followers: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "") }
            val result = userRepository.getConnections(userId, followers)
            _uiState.update {
                if (result.isSuccess) {
                    it.copy(users = result.getOrDefault(emptyList()), isLoading = false)
                } else {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudo cargar la lista."
                    )
                }
            }
        }
    }
}
