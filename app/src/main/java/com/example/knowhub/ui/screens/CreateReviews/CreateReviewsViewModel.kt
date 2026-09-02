package com.example.knowhub.ui.screens.CreateReviews

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CreateReviewsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CreateReviewsState())
    val uiState: StateFlow<CreateReviewsState> = _uiState

    fun updateClase(input: String) {
        _uiState.update { it.copy(clase = input) }
    }

    fun updateTituloMateria(input: String) {
        _uiState.update { it.copy(tituloMateria = input) }
    }

    fun updateNombreProfesor(input: String) {
        _uiState.update { it.copy(nombreProfesor = input) }
    }

    fun updateResena(input: String) {
        _uiState.update { it.copy(resena = input) }
    }
}
