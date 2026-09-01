package com.example.knowhub.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.knowhub.ui.screens.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState

    fun updateNombre (input: String){
        _uiState.update { it.copy( nombre =  input) }
    }

    fun guardarBottonPressed () {
        if (_uiState.value.nombre.isNullOrEmpty()
        ) {
            _uiState.update { it.copy(mostrarMensajeErrorGuardar = true, errorMessageGuardar = "No ha puesto un nombre nuevo") }
        }

    }
    fun cancelarBottonPressed() {

    }
    fun subirfotoBottonPressed (){

    }
    fun eliminarcuentaBottonPressed () {

    }
    fun cambiarcontraseñaBottonPressed (){

    }
    fun cambiairCorreoBottonPressed (){

    }




}