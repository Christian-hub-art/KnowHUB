package com.example.knowhub.ui.screens.reviewMap

import com.example.knowhub.data.Review

// Estado de carga, reseñas disponibles y selección actual del mapa.
data class ReviewMapState(
    val mapboxToken: String = "",
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedReview: Review? = null,
    val cameraLatitude: Double? = null,
    val cameraLongitude: Double? = null,
    val cameraZoom: Double = 10.0
)
