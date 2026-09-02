package com.example.knowhub.ui.screens.completeReviews

import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.Review
//Estado que representa los datos necesarios para la pantalla de reseñas completas.
data class CompleteReviewsState(
    val generalReview: GeneralReview = GeneralReview(0, "", "", 0, 0, 0, "", emptyList()),
    val allReviews: List<Review> = emptyList()
)
