package com.example.knowhub.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.knowhub.ui.screens.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
//ViewModel encargado de gestionar la lógica de negocio y el estado del perfil de usuario.
class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState
    //Actualiza el valor del nombre de usuario ingresado en la interfaz.
    fun updateNombre (input: String){
        _uiState.update { it.copy( nombre =  input) }
    }
    //Valida y procesa la acción de guardar los cambios del perfil.
    fun guardarBottonPressed () {
        if (_uiState.value.nombre.isNullOrEmpty()
        ) {
            _uiState.update { it.copy(mostrarMensajeErrorGuardar = true, errorMessageGuardar = "No ha puesto un nombre nuevo") }
        }

    }//Cancela las ediciones realizadas y restablece los valores
    fun cancelarBottonPressed() {

    }//Gestiona el evento de subir una nueva foto de perfil.
    fun subirfotoBottonPressed (){

    }//Gestiona el evento de eliminación de la cuenta de usuario.
    fun eliminarcuentaBottonPressed () {

    }//Gestiona el flujo de cambio de contraseña.
    fun cambiarcontraseñaBottonPressed (){

    }// Gestiona el flujo de cambio de correo electrónico.
    fun cambiairCorreoBottonPressed (){

    }




}