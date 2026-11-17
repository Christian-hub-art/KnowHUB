package com.example.knowhub.ui.screens.reviewMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.BuildConfig
import com.example.knowhub.data.Review
import com.example.knowhub.data.datasource.repository.LocationRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewMapViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ReviewMapState(mapboxToken = BuildConfig.MAPBOX_PUBLIC_TOKEN)
    )
    val uiState: StateFlow<ReviewMapState> = _uiState

    // Carga reseñas recientes con ubicación para alimentar los marcadores del mapa.
    fun refresh() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = reviewRepository.getReviewsPublishedInLast24Hours()
            _uiState.update {
                val reviews = result.getOrDefault(emptyList())
                val locatedReviews = reviews.filter { it.latitude != null && it.longitude != null }
                val latitude = locatedReviews.mapNotNull { it.latitude }.average()
                val longitude = locatedReviews.mapNotNull { it.longitude }.average()
                it.copy(
                    reviews = reviews,
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message,
                    selectedReviews = emptyList(),
                    cameraLatitude = latitude.takeIf { value -> value.isFinite() },
                    cameraLongitude = longitude.takeIf { value -> value.isFinite() },
                    cameraUpdateId = it.cameraUpdateId + 1
                )
            }
        }
    }

    fun selectReviews(reviews: List<Review>) {
        _uiState.update { it.copy(selectedReviews = reviews) }
    }

    fun clearSelectedReviews() {
        _uiState.update { it.copy(selectedReviews = emptyList()) }
    }

    fun locateMe() {
        if (_uiState.value.isLocating) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLocating = true, locationErrorMessage = null) }
            val result = locationRepository.getCurrentLocation()
            val location = result.getOrNull()
            _uiState.update {
                it.copy(
                    isLocating = false,
                    locationErrorMessage = result.exceptionOrNull()?.localizedMessage
                        ?: if (location == null) "No se pudo obtener tu ubicación." else null,
                    userLatitude = location?.latitude ?: it.userLatitude,
                    userLongitude = location?.longitude ?: it.userLongitude,
                    cameraLatitude = location?.latitude ?: it.cameraLatitude,
                    cameraLongitude = location?.longitude ?: it.cameraLongitude,
                    cameraZoom = if (location != null) 15.0 else it.cameraZoom,
                    cameraUpdateId = if (location != null) it.cameraUpdateId + 1 else it.cameraUpdateId
                )
            }
        }
    }
}
