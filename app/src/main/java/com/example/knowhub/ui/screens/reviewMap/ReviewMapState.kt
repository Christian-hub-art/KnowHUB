package com.example.knowhub.ui.screens.reviewMap

import com.example.knowhub.data.Review

data class ReviewMapState(
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedReview: Review? = null
)
