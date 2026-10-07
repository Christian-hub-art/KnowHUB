package com.example.knowhub.ui.screens.completeReviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.services.AsignaturaRetrofitService
import com.example.knowhub.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CompleteReviewsViewModel @Inject constructor(
    private val asignaturaService: AsignaturaRetrofitService,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompleteReviewsState(isLoading = true))
    val uiState: StateFlow<CompleteReviewsState> = _uiState

    fun loadData(idAsignatura: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }

        try {
            val asignaturaDeferred = async { asignaturaService.getAsignaturaById(idAsignatura) }
            val reviewsDeferred = async { reviewRepository.getReviewsByAsignatura(idAsignatura) }

            val materia = asignaturaDeferred.await()
            val reviewsResult = reviewsDeferred.await()

            reviewsResult.fold(
                onSuccess = { reviewsList ->
                    _uiState.update {
                        it.copy(
                            asignatura = materia,
                            allReviews = reviewsList,
                            isLoading = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "No se pudieron cargar las reseñas: ${error.message}"
                        )
                    }
                }
            )
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = "No se pudo cargar el detalle: ${e.message}"
                )
            }
        }
    }
}