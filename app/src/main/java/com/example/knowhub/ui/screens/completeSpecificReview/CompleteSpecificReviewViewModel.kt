package com.example.knowhub.ui.screens.completeSpecificReview

import androidx.lifecycle.ViewModel
import com.example.knowhub.data.local.localCommentProvider
import com.example.knowhub.data.local.localReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CompleteSpecificReviewViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CompleteSpecificReviewState())
    val uiState: StateFlow<CompleteSpecificReviewState> = _uiState

    fun loadData(reviewId: Int) {
        val review = localReviewProvider.Reviews.find { it.id == reviewId }
        val allComments = localCommentProvider.comments
        
        if (review != null) {
            _uiState.update { 
                it.copy(
                    review = review,
                    comments = allComments,
                )
            }
        }
    }
}
