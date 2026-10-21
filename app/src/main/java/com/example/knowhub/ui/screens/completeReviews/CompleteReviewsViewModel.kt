package com.example.knowhub.ui.screens.completeReviews

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localGeneralReviewProvider
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.UserRepository
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
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
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

    fun paraTiButtonPressed() {
        _uiState.update { it.copy(soloSiguiendo = false) }
    }

    fun siguiendoButtonPressed() {
        val userId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            val result = userRepository.getFollowing(userId)
            if (result.isSuccess) {
                val followingIds = result.getOrNull()?.map { it.id } ?: emptyList()
                _uiState.update { it.copy(followingIds = followingIds, soloSiguiendo = true) }
            } else {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudieron cargar los usuarios que sigues"
                    )
                }
            }
        }
    }
}
