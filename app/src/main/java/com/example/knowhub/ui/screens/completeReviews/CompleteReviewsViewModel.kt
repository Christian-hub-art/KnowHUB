package com.example.knowhub.ui.screens.completeReviews

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.data.local.localReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
//ViewModel encargado de gestionar la lógica de negocio
class CompleteReviewsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteReviewsState())
    val uiState: StateFlow<CompleteReviewsState> = _uiState
    //Carga los datos correspondientes al ID de la reseña general recibida
    fun loadData(generalReviewId: Int) {
        val generalReview = localGeneralReviewProvider.generalReviews.find { it.id == generalReviewId }
        val allReviews = localReviewProvider.Reviews
        if (generalReview != null) {
            _uiState.update { it.copy(generalReview = generalReview, allReviews = allReviews) }
        }
    }
}
