package com.example.knowhub.ui.screens.completeReviews

import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.Review
//Estado que representa los datos necesarios para la pantalla de reseñas completas.
data class CompleteReviewsState(
    val generalReview: GeneralReview = GeneralReview(0, "", "", 0, 0, 0, "", emptyList()),
    val allReviews: List<Review> = emptyList(),
    val followingUserIds: Set<String> = emptySet(),
    val followingErrorMessage: String = "",
    val showFollowingOnly: Boolean = false,
    val isUpdatingReviewLike: Boolean = false,
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false
) {
    val visibleReviews: List<Review>
        get() = if (showFollowingOnly) {
            allReviews.filter { it.userId in followingUserIds }
        } else {
            allReviews
        }
}
