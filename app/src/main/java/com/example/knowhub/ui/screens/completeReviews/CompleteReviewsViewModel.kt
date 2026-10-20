package com.example.knowhub.ui.screens.completeReviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import com.example.knowhub.data.local.localGeneralReviewProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CompleteReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteReviewsState())
    val uiState: StateFlow<CompleteReviewsState> = _uiState
    private var reviewsListenerJob: Job? = null
    private var loadedFollowingIds = false

    fun loadData(generalReviewId: Int) {
        val generalReview = localGeneralReviewProvider.generalReviews
            .find { it.id == generalReviewId }
        _uiState.update {
            it.copy(
                generalReview = generalReview ?: it.generalReview,
                mostrarMensajeError = false
            )
        }

        if (!loadedFollowingIds) {
            loadedFollowingIds = true
            viewModelScope.launch {
                val result = userRepository.getFollowingIds()
                if (result.isSuccess) {
                    _uiState.update {
                        it.copy(
                            followingUserIds = result.getOrDefault(emptySet()),
                            followingErrorMessage = ""
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            followingErrorMessage = result.exceptionOrNull()?.message
                                ?: "No se pudo cargar la lista de seguidos."
                        )
                    }
                }
            }
        }

        if (reviewsListenerJob?.isActive == true) return

        reviewsListenerJob = viewModelScope.launch {
            reviewRepository.getReviewsLive()
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            mostrarMensajeError = true,
                            errorMessage = error.message ?: "No se pudieron actualizar las reseñas"
                        )
                    }
                }
                .collect { reviews ->
                    _uiState.update {
                        it.copy(
                            allReviews = reviews,
                            mostrarMensajeError = false,
                            errorMessage = ""
                        )
                    }
                }
        }
    }

    fun showAllReviews() {
        _uiState.update { it.copy(showFollowingOnly = false) }
    }

    fun showFollowedUsersReviews() {
        _uiState.update { it.copy(showFollowingOnly = true) }
    }

    fun rateReview(reviewId: String, rating: Int) {
        viewModelScope.launch {
            val result = reviewRepository.rateReview(reviewId, rating)
            if (result.isFailure) {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudo guardar la calificación."
                    )
                }
            }
        }
    }
}
