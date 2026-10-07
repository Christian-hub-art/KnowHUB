package com.example.knowhub.ui.screens.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.repository.ReviewRepository
import com.example.knowhub.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewState())
    val uiState: StateFlow<ReviewState> = _uiState.asStateFlow()

    init {
        loadReviews()
    }

    fun loadReviews() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = reviewRepository.getReviews()
            result.onSuccess { reviewsList ->
                _uiState.update { currentState ->
                    currentState.copy(
                        reviews = reviewsList,
                        isLoading = false
                    )
                }
            }.onFailure { exception ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Error al cargar reseñas"
                    )
                }
            }
        }
    }

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            val result = reviewRepository.deleteReview(reviewId)
            result.onSuccess {
                loadReviews()
            }.onFailure { exception ->
                _uiState.update { currentState ->
                    currentState.copy(
                        errorMessage = exception.message ?: "Error al eliminar reseña"
                    )
                }
    fun getUserReviews(userId: String) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            val result = userRepository.getUserReview(userId)

            if (result.isSuccess) {

                val reviews = result.getOrNull() ?: emptyList()

                _uiState.value = _uiState.value.copy(
                    reviews = reviews,
                    isLoading = false
                )

            } else {

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }
}
