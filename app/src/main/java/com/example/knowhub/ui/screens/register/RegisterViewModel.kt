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
//viewModel encargado de manejar la lógica de negocio y validación de formularios para la pantalla de Registro.
class RegisterViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState
    //Actualiza el campo del nombre completo y oculta el mensaje de error activo.
    fun updateNombre(input: String){
        _uiState.update { it.copy(nombre = input) }
    }//Actualiza el correo electrónico y oculta el mensaje de error activo.
    fun updateCorreoElectronico(input: String){
        _uiState.update { it.copy( correoElectronico =  input) }
    }
    //Actualiza el nombre de usuario y oculta el mensaje de error activo.
    fun updateNombreUsuario(input: String){
        _uiState.update { it.copy( nombreUsuario =  input) }
    }//Actualiza la contraseña y oculta el mensaje de error activo.
    fun updateContrasena(input: String){
        _uiState.update { it.copy( contrasena =  input) }
    }//Actualiza la confirmación de la contraseña y oculta el mensaje de error activo.
    fun updateConfirmarContrasena(input: String){
        _uiState.update { it.copy( confirmarContrasena =  input) }
    }
    //Valida la información del formulario e inicia el registro si los campos son correctos.
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