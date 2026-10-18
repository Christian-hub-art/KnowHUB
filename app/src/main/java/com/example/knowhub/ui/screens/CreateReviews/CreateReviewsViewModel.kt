package com.example.knowhub.ui.screens.CreateReviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.ReviewRepository
import com.example.knowhub.data.datasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//ViewModel encargado de gestionar el estado del formulario de creación de reseñas.
@HiltViewModel
class CreateReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateReviewsState())
    val uiState: StateFlow<CreateReviewsState> = _uiState

    init {
        getUserName()
    }


    private fun getUserName() {
        val userId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            val result = userRepository.getUserById(userId)
            if (result.isSuccess) {
                _uiState.update { it.copy(userName = result.getOrNull()?.userName ?: "") }
            }
        }
    }


    //Carga una reseña existente para editarla (equivale a getTweetById del profesor).
    fun loadReview(reviewId: String) {
        if (_uiState.value.reviewId == reviewId) return
        viewModelScope.launch {
            val result = reviewRepository.getReviewById(reviewId)
            val review = result.getOrNull()
            if (review == null) {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar la reseña"
                    )
                }
                return@launch
            }
            if (review.userId != authRepository.currentUser?.uid) {
                _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "Solo el autor puede editar esta reseña") }
                return@launch
            }
            _uiState.update {
                it.copy(
                    reviewId = reviewId,
                    clase = review.clase,
                    tituloMateria = review.nombreAsignatura,
                    nombreProfesor = review.nombreProfesor,
                    resena = review.descripcion,
                    mostrarMensajeError = false
                )
            }
        }
    }

    fun publicarButtonPressed() {
        val state = _uiState.value
        if (state.clase.isBlank() || state.tituloMateria.isBlank() ||
            state.nombreProfesor.isBlank() || state.resena.isBlank()
        ) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "Todos los campos son obligatorios") }
            return
        }
        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "No hay un usuario autenticado") }
            return
        }
        if (state.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeError = false) }
            // Si hay reviewId se edita; si no, se crea (como createTweet del profesor)
            val result = if (state.reviewId != null) {
                reviewRepository.updateReview(
                    reviewId = state.reviewId,
                    clase = state.clase,
                    nombreAsignatura = state.tituloMateria,
                    nombreProfesor = state.nombreProfesor,
                    descripcion = state.resena
                )
            } else {
                reviewRepository.createReview(
                    userId = userId,
                    clase = state.clase,
                    nombreAsignatura = state.tituloMateria,
                    nombreProfesor = state.nombreProfesor,
                    descripcion = state.resena
                )
            }
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, navigateBack = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo guardar la reseña"
                    )
                }
            }
        }
    }

    //Cancela la creación y vuelve a la pantalla anterior.
    fun cancelarButtonPressed() {
        _uiState.update { it.copy(navigateBack = true) }
    }
    //Actualiza el valor del código de clase.
    fun updateClase(input: String) {
        _uiState.update { it.copy(clase = input) }
    }
    //Actualiza el título de la materia.
    fun updateTituloMateria(input: String) {
        _uiState.update { it.copy(tituloMateria = input) }
    }
    //Actualiza el nombre del profesor.
    fun updateNombreProfesor(input: String) {
        _uiState.update { it.copy(nombreProfesor = input) }
    }
    //Actualiza el contenido del texto de la reseña.
    fun updateResena(input: String) {
        _uiState.update { it.copy(resena = input) }
    }
}
