package com.example.knowhub.ui.screens.reviewMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.BuildConfig
import com.example.knowhub.data.Review
import com.example.knowhub.data.datasource.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewMapViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
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
                    selectedReview = null,
                    cameraLatitude = latitude.takeIf { value -> value.isFinite() },
                    cameraLongitude = longitude.takeIf { value -> value.isFinite() }
                )
            }
        }
    }

    fun selectReview(review: Review?) {
        _uiState.update { it.copy(selectedReview = review) }
    }
}
