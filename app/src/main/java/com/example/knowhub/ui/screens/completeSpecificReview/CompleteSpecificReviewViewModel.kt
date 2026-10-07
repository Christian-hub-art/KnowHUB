package com.example.knowhub.ui.screens.completeSpecificReview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.repository.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CompleteSpecificReviewViewModel @Inject constructor(private val repository: CatalogRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteSpecificReviewState(isLoading = true))
    val uiState: StateFlow<CompleteSpecificReviewState> = _uiState

    fun loadData(reviewId: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }
        repository.reviewConComentarios(reviewId).fold(
            onSuccess = { (review, comments) ->
                _uiState.update { it.copy(review = review, comments = comments, isLoading = false) }
            },
            onFailure = { error ->
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudieron cargar los comentarios: ${error.message}")
                }
            }
        )
    }
}



