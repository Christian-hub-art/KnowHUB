package com.example.knowhub.ui.screens.BusquedaPerfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BusquedaPerfilViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusquedaPerfilState())
    val uiState: StateFlow<BusquedaPerfilState> = _uiState.asStateFlow()

    fun getUserProfile(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeError = false) }
            val result = userRepository.getUserById(userId)
            if (result.isSuccess) {
                val user = result.getOrNull()
                _uiState.update {
                    it.copy(
                        userName = user?.userName ?: "",
                        fotoPerfil = user?.foto?.ifBlank { null } ?: it.fotoPerfil,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar el perfil"
                    )
                }
            }
        }
    }

    fun getUserReviews(userId: String) {
        viewModelScope.launch {
            val result = reviewRepository.getReviewsByUser(userId)
            if (result.isSuccess) {
                val reviews = result.getOrNull() ?: emptyList()
                _uiState.update {
                    it.copy(
                        reviews = reviews,
                        // Si el perfil aún no tiene foto guardada, se usa la de sus reseñas
                        fotoPerfil = it.fotoPerfil ?: reviews.firstNotNullOfOrNull { r -> r.fotoAutor }
                    )
                }
            }
        }
    }
}
