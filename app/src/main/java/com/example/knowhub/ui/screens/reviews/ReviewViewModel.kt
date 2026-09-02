package com.example.knowhub.ui.screens.reviews

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
//ViewModel encargado de la gestión de datos y estado para la pantalla de Reseñas.
class ReviewViewModel : ViewModel() {

    // Encapsulamiento: Mutable privado, Inmutable expuesto
    private val _uiState = MutableStateFlow(ReviewState())
    val uiState: StateFlow<ReviewState> = _uiState.asStateFlow()

    init {
        loadReviews()
    }

    private fun loadReviews() {
        val lista = localReviewProvider.Reviews

        _uiState.update { currentState ->
            currentState.copy(
                reviews = lista,
                isLoading = false
            )
        }
    }
}