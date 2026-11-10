package com.example.knowhub.ui.screens.reviewMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.Review
import com.example.knowhub.data.datasource.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewMapViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReviewMapState())
    val uiState: StateFlow<ReviewMapState> = _uiState

    fun refresh() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = reviewRepository.getReviewsPublishedInLast24Hours()
            _uiState.update {
                it.copy(
                    reviews = result.getOrDefault(emptyList()),
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message,
                    selectedReview = null
                )
            }
        }
    }

    fun selectReview(review: Review?) {
        _uiState.update { it.copy(selectedReview = review) }
    }
}
