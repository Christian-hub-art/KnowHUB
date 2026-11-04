package com.example.knowhub.ui.screens.CreateMateria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.datasource.repository.AuthRepository
import com.example.knowhub.data.datasource.repository.GeneralReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//ViewModel encargado del formulario para crear, editar y eliminar materias.
@HiltViewModel
class CreateMateriaViewModel @Inject constructor(
    private val generalReviewRepository: GeneralReviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateMateriaState())
    val uiState: StateFlow<CreateMateriaState> = _uiState

    fun loadMateria(generalReviewId: Int) {
        if (_uiState.value.generalReviewId == generalReviewId) return
        viewModelScope.launch {
            val result = generalReviewRepository.getGeneralReviewById(generalReviewId)
            val materia = result.getOrNull()
            if (materia == null) {
                _uiState.update {
                    it.copy(
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar la materia"
                    )
                }
                return@launch
            }
            _uiState.update {
                it.copy(
                    generalReviewId = generalReviewId,
                    nombreMateria = materia.nombreMateria,
                    nombreProfesor = materia.nombreProfesor,
                    codigo = materia.codigoAsignatura.toString(),
                    dificultad = materia.dificultadMedia,
                    hashtags = materia.Hashtags.joinToString(" "),
                    creadorId = materia.userId,
                    mostrarMensajeError = !esCreador(materia.userId),
                    errorMessage = if (esCreador(materia.userId)) "" else "Solo quien creó la materia puede editarla"
                )
            }
        }
    }

    // Las materias sin creador (creadas antes de guardar userId) las puede editar cualquiera
    private fun esCreador(creadorId: String): Boolean {
        return creadorId.isEmpty() || creadorId == authRepository.currentUser?.uid
    }

    fun guardarButtonPressed() {
        val state = _uiState.value
        if (state.nombreMateria.isBlank() || state.nombreProfesor.isBlank() || state.codigo.isBlank()) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "Todos los campos son obligatorios") }
            return
        }
        val codigo = state.codigo.trim().toIntOrNull()
        if (codigo == null || codigo <= 0) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "El código debe ser un número") }
            return
        }
        if (state.isLoading) return
        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "No hay un usuario autenticado") }
            return
        }
        if (state.generalReviewId != null && !esCreador(state.creadorId)) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "Solo quien creó la materia puede editarla") }
            return
        }

        // "#calculo derivadas" -> ["#calculo", "#derivadas"]
        val hashtags = state.hashtags.split(" ", ",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { if (it.startsWith("#")) it else "#$it" }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeError = false) }
            val result = if (state.generalReviewId != null) {
                generalReviewRepository.updateGeneralReview(
                    id = state.generalReviewId,
                    nombreMateria = state.nombreMateria.trim(),
                    nombreProfesor = state.nombreProfesor.trim(),
                    dificultadMedia = state.dificultad,
                    hashtags = hashtags
                )
            } else {
                generalReviewRepository.createGeneralReview(
                    nombreMateria = state.nombreMateria.trim(),
                    nombreProfesor = state.nombreProfesor.trim(),
                    codigoAsignatura = codigo,
                    dificultadMedia = state.dificultad,
                    hashtags = hashtags,
                    userId = userId
                )
            }
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, navigateBack = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo guardar la materia"
                    )
                }
            }
        }
    }

    fun eliminarButtonPressed() {
        val generalReviewId = _uiState.value.generalReviewId ?: return
        if (_uiState.value.isLoading) return
        if (!esCreador(_uiState.value.creadorId)) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "Solo quien creó la materia puede eliminarla") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mostrarMensajeError = false) }
            val result = generalReviewRepository.deleteGeneralReview(generalReviewId)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, materiaEliminada = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mostrarMensajeError = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo eliminar la materia"
                    )
                }
            }
        }
    }

    fun cancelarButtonPressed() {
        _uiState.update { it.copy(navigateBack = true) }
    }

    fun updateNombreMateria(input: String) {
        _uiState.update { it.copy(nombreMateria = input) }
    }

    fun updateNombreProfesor(input: String) {
        _uiState.update { it.copy(nombreProfesor = input) }
    }

    fun updateCodigo(input: String) {
        _uiState.update { it.copy(codigo = input) }
    }

    fun updateDificultad(input: String) {
        _uiState.update { it.copy(dificultad = input) }
    }

    fun updateHashtags(input: String) {
        _uiState.update { it.copy(hashtags = input) }
    }
}
