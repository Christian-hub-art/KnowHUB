package com.example.knowhub.ui.screens.reviewMap

import com.example.knowhub.data.Review

// Estado de carga, reseñas disponibles y selección actual del mapa.
data class ReviewMapState(
    val mapboxToken: String = "",
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedReviews: List<Review> = emptyList(),
    val cameraLatitude: Double? = null,
    val cameraLongitude: Double? = null,
    val cameraZoom: Double = 10.0,
    val cameraUpdateId: Long = 0,
    val isLocating: Boolean = false,
    val locationErrorMessage: String? = null,
    val userLatitude: Double? = null,      // ubicación del usuario para el marcador "Estás aquí"
    val userLongitude: Double? = null
)
