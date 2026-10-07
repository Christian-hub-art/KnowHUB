package com.example.knowhub.ui.screens.completeReviews

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
class CompleteReviewsViewModel @Inject constructor(private val repository: CatalogRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteReviewsState(isLoading = true))
    val uiState: StateFlow<CompleteReviewsState> = _uiState

    fun loadData(id: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }
        repository.asignaturaConResenas(id).fold(
            onSuccess = { (materia, reviews) ->
                _uiState.update { it.copy(generalReview = materia, allReviews = reviews, isLoading = false) }
            },
            onFailure = { error ->
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudo cargar el detalle: ${error.message}")
                }
            }
        )
    }
}



