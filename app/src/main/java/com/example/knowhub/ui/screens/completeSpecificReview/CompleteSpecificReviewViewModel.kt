package com.example.knowhub.ui.screens.completeSpecificReview

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localCommentProvider
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.ReviewRepository
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class CompleteSpecificReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteSpecificReviewState())
    val uiState: StateFlow<CompleteSpecificReviewState> = _uiState

    //Obtiene la reseña desde Firestore usando el id del documento.
    fun loadData(reviewId: String) {
        // Los comentarios siguen siendo locales hasta implementar su parte en Firestore
        _uiState.update { it.copy(comments = localCommentProvider.comments) }

        viewModelScope.launch {
            val result = reviewRepository.getReviewById(reviewId)
            if (result.isSuccess) {
                _uiState.update { it.copy(review = result.getOrNull(), mostrarMensajeError = false) }
            } else {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar la reseña"
                    )
                }
            }
        }
    }
}
