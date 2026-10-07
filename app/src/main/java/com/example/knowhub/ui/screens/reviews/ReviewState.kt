package com.example.knowhub.ui.screens.reviews

import com.example.knowhub.data.Review

data class ReviewState(
    val usuario: String = "",
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
