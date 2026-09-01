package com.example.knowhub.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.DataInput

class RegisterViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState

    fun updateNombre(input: String){
        _uiState.update { it.copy(nombre = input) }
    }
    fun updateCorreoElectronico(input: String){
        _uiState.update { it.copy( correoElectronico =  input) }
    }

    fun updateNombreUsuario(input: String){
        _uiState.update { it.copy( nombreUsuario =  input) }
    }
    fun updateContrasena(input: String){
        _uiState.update { it.copy( contrasena =  input) }
    }
    fun updateConfirmarContrasena(input: String){
        _uiState.update { it.copy( confirmarContrasena =  input) }
    }

    fun registrarseSesionButtonPressed () {
        if (_uiState.value.nombre.isNullOrEmpty() ||
            _uiState.value.correoElectronico.isNullOrEmpty() ||
            _uiState.value.nombreUsuario.isNullOrEmpty() ||
            _uiState.value.contrasena.isNullOrEmpty() ||
            _uiState.value.confirmarContrasena.isNullOrEmpty()
        ) {
           _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "Todos los campos son obligatorios") }
        }else{
            if (_uiState.value.contrasena !=
                _uiState.value.confirmarContrasena) {
                _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "Las contraseñas no coinciden")
                }
            }else{
                _uiState.update { it.copy(navigateInicio = true) }
            }
        }
    }
    fun loginButtonPressed () {
        _uiState.update { it.copy(navigateLogin = true) }
    }




}