package com.example.knowhub.ui.screens.login

import androidx.lifecycle.ViewModel
import com.example.knowhub.ui.screens.register.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    fun updateNombreOCorreo(input: String){
        _uiState.update { it.copy( nombreOCorreo =  input) }
    }

    fun updateContrasena(input: String){
        _uiState.update { it.copy( contrasena =  input) }
    }

    fun inciarSesionButtonPressed () {
        if (_uiState.value.nombreOCorreo.isNullOrEmpty() ||
            _uiState.value.contrasena.isNullOrEmpty()
        ) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "Todos los campos son obligatorios") }
        }else{
                _uiState.update { it.copy(navigateInicio = true) }

        }
    }

    fun registerButtonPressed () {
        _uiState.update { it.copy(navigateRegister = true) }
    }

    fun continuarButtonPressed () {
        _uiState.update { it.copy(navigateContinuar = true) }
    }


}