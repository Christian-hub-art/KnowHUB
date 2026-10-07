package com.example.knowhub.ui.screens.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel encargado de la gestión de datos y estado para la pantalla de Reseñas.
@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewState())
    val uiState: StateFlow<ReviewState> = _uiState.asStateFlow()

    fun getUserReviews(userId: String) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            val result = userRepository.getUserReview(userId)

            if (result.isSuccess) {

                val reviews = result.getOrNull() ?: emptyList()

                _uiState.value = _uiState.value.copy(
                    reviews = reviews,
                    isLoading = false
                )

            } else {

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }
}