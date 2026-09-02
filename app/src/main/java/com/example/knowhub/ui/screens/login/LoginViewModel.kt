package com.example.knowhub.ui.screens.login

import androidx.lifecycle.ViewModel
import com.example.knowhub.ui.screens.register.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
//ViewModel que gestiona el estado y las acciones del formulario de inicio de sesión.
class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState
    //Actualiza el identificador de usuario o correo.
    fun updateNombreOCorreo(input: String){
        _uiState.update { it.copy( nombreOCorreo =  input) }
    }
    //Actualiza la contraseña del usuario.
    fun updateContrasena(input: String){
        _uiState.update { it.copy( contrasena =  input) }
    }
    //Valida las credenciales ingresadas e inicia la sesión o dispara un mensaje de error
    fun inciarSesionButtonPressed () {
        if (_uiState.value.nombreOCorreo.isNullOrEmpty() ||
            _uiState.value.contrasena.isNullOrEmpty()
        ) {
            _uiState.update { it.copy(mostrarMensajeError = true, errorMessage = "Todos los campos son obligatorios") }
        }else{
                _uiState.update { it.copy(navigateInicio = true) }

        }
    }
    //Dispara la navegación a la pantalla de registro.
    fun registerButtonPressed () {
        _uiState.update { it.copy(navigateRegister = true) }
    }
    //Dispara la navegación en modo invitado (sin registro).
    fun continuarButtonPressed () {
        _uiState.update { it.copy(navigateContinuar = true) }
    }


}