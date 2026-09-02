package com.example.knowhub.ui.screens.completeReviews

import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.Review

data class CompleteReviewsState(
    val generalReview: GeneralReview = GeneralReview(0, "", "", 0, 0, 0, "", emptyList()),
    val allReviews: List<Review> = emptyList()
)
