package com.example.knowhub.ui.screens.completeReviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.GeneralReviewRepository
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
    private val authRepository: AuthRepository,
    private val generalReviewRepository: GeneralReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteReviewsState())
    val uiState: StateFlow<CompleteReviewsState> = _uiState
    //Carga desde Firestore la materia correspondiente al ID recibido
    fun loadData(generalReviewId: Int) {
        _uiState.update { it.copy(currentUserId = authRepository.currentUser?.uid ?: "") }
        viewModelScope.launch {
            val result = generalReviewRepository.getGeneralReviewById(generalReviewId)
            if (result.isSuccess) {
                _uiState.update { it.copy(generalReview = result.getOrNull() ?: it.generalReview) }
            } else {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar la materia"
                    )
                }
            }
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
