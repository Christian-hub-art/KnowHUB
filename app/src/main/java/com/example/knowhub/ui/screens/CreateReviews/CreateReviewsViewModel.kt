package com.example.knowhub.ui.screens.CreateReviews

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
//ViewModel encargado de gestionar el estado del formulario de creación de reseñas.
class CreateReviewsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CreateReviewsState())
    val uiState: StateFlow<CreateReviewsState> = _uiState
    //Actualiza el valor del código de clase.
    fun updateClase(input: String) {
        _uiState.update { it.copy(clase = input) }
    }
    //Actualiza el título de la materia.
    fun updateTituloMateria(input: String) {
        _uiState.update { it.copy(tituloMateria = input) }
    }
    //Actualiza el nombre del profesor.
    fun updateNombreProfesor(input: String) {
        _uiState.update { it.copy(nombreProfesor = input) }
    }
    //Actualiza el contenido del texto de la reseña.
    fun updateResena(input: String) {
        _uiState.update { it.copy(resena = input) }
    }
}
