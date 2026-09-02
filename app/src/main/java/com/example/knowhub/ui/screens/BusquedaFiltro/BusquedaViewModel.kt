package com.example.knowhub.ui.screens.BusquedaFiltro

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.ui.screens.busquedaFiltro.busquedaState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BusquedaViewModel : ViewModel() {

    // Encapsulamiento: Mutable privado, Inmutable expuesto
    private val _uiState = MutableStateFlow(busquedaState())
    val uiState: StateFlow<busquedaState> = _uiState.asStateFlow()

    init {
        loadGeneralReviews()
    }

    private fun loadGeneralReviews() {
        val lista = localGeneralReviewProvider.generalReviews

        _uiState.update { currentState ->
            currentState.copy(
                reviews = lista,
                isLoading = false
            )
        }
    }

    // Función de evento para actualizar el texto de búsqueda
    fun onFiltroChange(nuevoFiltro: String) {
        _uiState.update { currentState ->
            currentState.copy(filtro = nuevoFiltro)
        }
    }
}