package com.example.knowhub.ui.screens.BusquedaFiltro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.GeneralReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//ViewModel encargado de gestionar la lógica de negocio y el estado de la pantalla de búsqueda.

@HiltViewModel
class BusquedaViewModel @Inject constructor(
    private val generalReviewRepository: GeneralReviewRepository
): ViewModel() {

    // Encapsulamiento: Mutable privado, Inmutable expuesto
    private val _uiState = MutableStateFlow(BusquedaState())
    val uiState: StateFlow<BusquedaState> = _uiState.asStateFlow()

    init {// Carga inicial de las reseñas al instanciar el ViewModel
        loadGeneralReviews()
    }

    private fun loadGeneralReviews() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeError = false) }
            generalReviewRepository.getGeneralReviewsLive()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mostrarMensajeError = true,
                            errorMessage = e.message ?: "No se pudieron cargar las materias"
                        )
                    }
                }
                .collect { materias ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            reviews = materias,
                            isLoading = false,
                            mostrarMensajeError = false
                        )
                    }
                }
        }
    }

    // Función de evento para actualizar el texto de búsqueda
    fun onFiltroChange(nuevoFiltro: String) {
        _uiState.update { currentState ->
            currentState.copy(filtro = nuevoFiltro)
        }
    }
}