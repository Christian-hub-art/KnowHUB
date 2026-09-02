package com.example.knowhub.ui.screens.reviews

import com.example.knowhub.data.Review

data class ReviewState(
    val usuario: String = "Leo6767",
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false
)