package com.example.knowhub.ui.screens.login


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.ui.screens.login.components.LoginMessage
import com.example.knowhub.ui.screens.login.components.TextosLogin
import com.example.knowhub.ui.screens.register.RegisterViewModel
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.LogoApp


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = LoginViewModel(),
    modifier: Modifier = Modifier
){
    val state by loginViewModel.uiState.collectAsState()
    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyLoginScreen(
            state.nombreOCorreo,
            state.contrasena,
            state.mostrarMensajeError,
            state.errorMessage,
            onCorreoOUsuarioChange = {loginViewModel.updateNombreOCorreo(it)},
            onContrasenaChange = {loginViewModel.updateContrasena(it)},
            inciarSesionButtonPressed = {loginViewModel.inciarSesionButtonPressed()},
            registerButtonPressed = {loginViewModel.registerButtonPressed()},
            continuarButtonPressed = {loginViewModel.continuarButtonPressed()}
        )
    }
}
@Composable
@Preview(showBackground = true)
fun LoginScreenPreview(){
    LoginScreen(
        loginViewModel = viewModel()
    )

}

@Composable
fun BodyLoginScreen(
    correoOUsuario: String,
    contrasena: String,
    mostrarMensajeError: Boolean,
    errorMessage: String,
    onCorreoOUsuarioChange: (String) ->Unit,
    onContrasenaChange: (String) ->Unit,
    inciarSesionButtonPressed: () -> Unit,
    registerButtonPressed: () -> Unit,
    continuarButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(10F))
        LogoApp()
        LoginMessage()
        Spacer(modifier = Modifier.weight(1.0F))

        TextosLogin(
            correoOUsuario,
            contrasena,
            onCorreoOUsuarioChange = {onCorreoOUsuarioChange(it)},
            onContrasenaChange = {onContrasenaChange(it)}
        )
        Spacer(modifier = Modifier.weight(0.5F))
        Text(stringResource(R.string.olvidaste_contrasena),
            fontFamily = BangersFont)
        Spacer(modifier = Modifier.weight(5.0F))



        AppButton(
            stringResource(R.string.inciar_sesi_n),
            primaryLight,
            tertiaryContainerLight,
            onClick = {
                inciarSesionButtonPressed()
            },
            modifier = modifier
                .height(30.dp)
                .width(280.dp))

        if(mostrarMensajeError){
            Text(errorMessage)
        }

        Spacer(modifier = Modifier.weight(0.5F))
        TextButton(
            onClick = {
                registerButtonPressed()
            }
        ) {
            Text(
                text = stringResource(R.string.no_tienes_cuenta_registrate),
                fontFamily = BangersFont,
                color = tertiaryContainerLight
            )
        }
        Spacer(modifier = Modifier.weight(10.0F))
        TextButton(
            onClick = {
                continuarButtonPressed()
            }
        ) {
            Text(
                text = stringResource(R.string.continuar_sin_registro),
                fontFamily = BangersFont,
                color = tertiaryContainerLight
            )
        }
        Spacer(modifier = Modifier.weight(1.0F))

    }

}