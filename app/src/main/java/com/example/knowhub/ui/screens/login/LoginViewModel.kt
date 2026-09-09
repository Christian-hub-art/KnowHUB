package com.example.knowhub.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowhub.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//ViewModel que gestiona el estado y las acciones del formulario de inicio de sesión.
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
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
            viewModelScope.launch {
                try {
                    authRepository.sigIn(
                        _uiState.value.nombreOCorreo,
                        _uiState.value.contrasena
                    )
                    _uiState.update { it.copy(navigateInicio = true) }
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    _uiState.update {
                        it.copy(
                            mostrarMensajeError = true,
                            errorMessage = "Correo o contraseña incorrectos"
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            mostrarMensajeError = true,
                            errorMessage = e.message ?: "Error al iniciar sesión"
                        )
                    }
                }
            }

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