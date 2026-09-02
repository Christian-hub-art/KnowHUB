package com.example.knowhub.ui.screens.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.ui.screens.register.components.SignUPMessage
import com.example.knowhub.ui.screens.register.components.TextosField
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.LogoApp
//Pantalla de registro de nuevos usuarios en la aplicación.
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = RegisterViewModel(),
    modifier: Modifier = Modifier
){
    val state by registerViewModel.uiState.collectAsState()

    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyRegisterScreen(
            state.nombre,
            state.correoElectronico,
            state.nombreUsuario,
            state.contrasena,
            state.confirmarContrasena,
            onNombreChange ={registerViewModel.updateNombre(it)},
            onCorreoElectronicoChange ={registerViewModel.updateCorreoElectronico(it)},
            onNombreUsuarioChange ={registerViewModel.updateNombreUsuario(it)},
            onContrasenaChange ={registerViewModel.updateContrasena(it)},
            onConfirmarContrasenaChange ={registerViewModel.updateConfirmarContrasena(it)},
            registrarseSesionButtonPressed = {registerViewModel.registrarseSesionButtonPressed()},
            loginButtonPressed = {registerViewModel.loginButtonPressed()},
            state.mostrarMensajeError,
            state.errorMessage

        )
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterScreenPreview(){
    RegisterScreen(
        registerViewModel = viewModel()
    )
}
//Maquetación y estructura del contenido para la pantalla de registro.
@Composable
fun BodyRegisterScreen(
    nombre: String,
    correoElectronico: String,
    nombreUsuario: String,
    contrasena: String,
    confirmarContrasena: String,
    onNombreChange: (String) -> Unit,
    onCorreoElectronicoChange: (String) -> Unit,
    onNombreUsuarioChange: (String) -> Unit,
    onContrasenaChange: (String) -> Unit,
    onConfirmarContrasenaChange: (String) -> Unit,
    registrarseSesionButtonPressed: () -> Unit,
    loginButtonPressed: () -> Unit,
    mostrarMensajeError: Boolean,
    errorMessage: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {

                LogoApp()
                SignUPMessage()
                Spacer(modifier = Modifier.weight(1.0f))

                TextosField(
                    nombre,
                    correoElectronico,
                    nombreUsuario,
                    contrasena,
                    confirmarContrasena,
                    onNombreChange = { onNombreChange(it) },
                    onCorreoElectronicoChange = { onCorreoElectronicoChange(it) },
                    onNombreUsuarioChange = { onNombreUsuarioChange(it) },
                    onContrasenaChange = { onContrasenaChange(it) },
                    onConfirmarContrasenaChange = { onConfirmarContrasenaChange(it) })

                Spacer(modifier = Modifier.height(10.dp))

                AppButton(
                    stringResource(R.string.registrarse),
                    primaryLight,
                    tertiaryContainerLight,
                    onClick = {
                       registrarseSesionButtonPressed()
                    },
                    modifier = modifier
                        .height(30.dp)
                        .width(280.dp)
                )

                if (mostrarMensajeError)
                    Text(
                        errorMessage
                    )

                Spacer(modifier = Modifier.weight(5.0F))


                TextButton(
                    onClick = {
                        loginButtonPressed()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.ya_tienes_cuenta_login),
                        fontFamily = BangersFont,
                        color = tertiaryContainerLight
                    )
                }

                Spacer(modifier = Modifier.weight(1.0f))

            }
        }
    }
}





