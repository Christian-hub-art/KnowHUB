package com.example.knowhub.ui.screens.completeReviews

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localGeneralReviewProvider
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.ReviewRepository
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
//ViewModel encargado de gestionar la lógica de negocio
@HiltViewModel
class CompleteReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteReviewsState())
    val uiState: StateFlow<CompleteReviewsState> = _uiState
    //Carga los datos correspondientes al ID de la reseña general recibida
    fun loadData(generalReviewId: Int) {
        val generalReview = localGeneralReviewProvider.generalReviews.find { it.id == generalReviewId }
        if (generalReview != null) {
            _uiState.update { it.copy(generalReview = generalReview) }
        }
        viewModelScope.launch {
            val result = reviewRepository.getReviews()
            if (result.isSuccess) {
                _uiState.update { it.copy(allReviews = result.getOrNull() ?: emptyList(), mostrarMensajeError = false) }
            } else {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudieron cargar las reseñas"
                    )
                }
            }
        }
    }
}
