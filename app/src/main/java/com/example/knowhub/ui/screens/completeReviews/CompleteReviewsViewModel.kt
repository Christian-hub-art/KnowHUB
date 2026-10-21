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
import kotlinx.coroutines.flow.catch
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
    }

    fun getAllReviews(generalReviewId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeError = false) }
            reviewRepository.getReviewsByGeneralReviewLive(generalReviewId)
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            errorMessage = e.message ?: "No se pudieron cargar las reseñas",
                            mostrarMensajeError = true,
                            isLoading = false
                        )
                    }
                }
                .collect { reviews ->
                    _uiState.update { it.copy(allReviews = reviews, isLoading = false, mostrarMensajeError = false) }
                }
        }
    }
}
