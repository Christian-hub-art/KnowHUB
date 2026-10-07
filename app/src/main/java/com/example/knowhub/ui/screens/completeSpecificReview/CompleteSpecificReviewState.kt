package com.example.knowhub.ui.screens.completeSpecificReview

import com.example.knowhub.data.Comment
import com.example.knowhub.data.Review

data class CompleteSpecificReviewState(
    val review: Review = Review("", "", "", "", "", "", "", "","","","",""),
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

