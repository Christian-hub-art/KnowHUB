package com.example.knowhub.ui.screens.completeReviews

import com.example.knowhub.data.Asignatura
import com.example.knowhub.data.Review

data class CompleteReviewsState(
    val asignatura: Asignatura? = null,
    val allReviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)


