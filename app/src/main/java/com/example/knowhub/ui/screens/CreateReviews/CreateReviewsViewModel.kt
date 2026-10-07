package com.example.knowhub.ui.screens.CreateReviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

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

    fun createReview(parentReviewId: String? = null, reviewId: String? = null) {
        val currentState = _uiState.value
        val userId = "1"

        viewModelScope.launch {
            val result = if (reviewId != null) {
                reviewRepository.updateReview(
                    reviewId = reviewId,
                    idAsignatura = currentState.clase,
                    idUsuario = userId,
                    descripcion = currentState.resena,
                    calificacion = currentState.calificacion,
                    nombreProfesor = currentState.nombreProfesor,
                    nombreAsignatura = currentState.tituloMateria,
                    parentReviewId = parentReviewId
                )
            } else {
                reviewRepository.createReview(
                    idAsignatura = currentState.clase,
                    idUsuario = userId,
                    descripcion = currentState.resena,
                    calificacion = currentState.calificacion,
                    nombreProfesor = currentState.nombreProfesor,
                    nombreAsignatura = currentState.tituloMateria,
                    parentReviewId = parentReviewId
                )
            }

            if (result.isSuccess) {
                _uiState.update { it.copy(navigateBack = true) }
            } else {
                _uiState.update { it.copy(error = result.exceptionOrNull()?.message) }
            }
        }
    }

    fun getReviewById(reviewId: String) {
        viewModelScope.launch {
            val result = reviewRepository.getReviewById(reviewId)
            if (result.isSuccess) {
                val review = result.getOrNull()
                if (review != null) {
                    _uiState.update {
                        it.copy(
                            clase = review.id.toString(),
                            tituloMateria = review.nombreAsignatura,
                            nombreProfesor = review.nombreProfesor,
                            resena = review.descripcion,
                            calificacion = review.calificacion
                        )
                    }
                }
            }
        }
    }
}
