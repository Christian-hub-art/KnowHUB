package com.example.knowhub.ui.screens.BusquedaFiltro

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.ui.screens.busquedaFiltro.BusquedaState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
//ViewModel encargado de gestionar la lógica de negocio y el estado de la pantalla de búsqueda.

@HiltViewModel
class BusquedaViewModel @Inject constructor(): ViewModel() {

    // Encapsulamiento: Mutable privado, Inmutable expuesto
    private val _uiState = MutableStateFlow(BusquedaState())
    val uiState: StateFlow<BusquedaState> = _uiState.asStateFlow()

    init {// Carga inicial de las reseñas al instanciar el ViewModel
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