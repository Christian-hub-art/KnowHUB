package com.example.knowhub.ui.screens.completeSpecificReview

import com.example.knowhub.data.Comment
import com.example.knowhub.data.Review

data class CompleteSpecificReviewState(
    val review: Review? = null,
    val comments: List<Comment> = emptyList(),
    val errorMessage: String = "",
    val mostrarMensajeError: Boolean = false
)
