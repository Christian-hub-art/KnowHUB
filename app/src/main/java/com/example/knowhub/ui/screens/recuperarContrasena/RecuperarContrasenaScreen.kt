package com.example.knowhub.ui.screens.recuperarContrasena

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.ui.screens.recuperarContrasena.components.TextoCorreoRecuperar
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.LogoApp

//Pantalla para pedir el correo de recuperación de contraseña.
@Composable
fun RecuperarContrasenaScreen(
    recuperarContrasenaViewModel: RecuperarContrasenaViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by recuperarContrasenaViewModel.uiState.collectAsState()
    Box(
        modifier = modifier
    ) {
        BackgroundImage()
        BodyRecuperarContrasenaScreen(
            correo = state.correo,
            mensaje = state.mensaje,
            mostrarMensaje = state.mostrarMensaje,
            isLoading = state.isLoading,
            onCorreoChange = { recuperarContrasenaViewModel.updateCorreo(it) },
            enviarButtonPressed = { recuperarContrasenaViewModel.enviarButtonPressed() },
            volverButtonPressed = { recuperarContrasenaViewModel.volverButtonPressed() }
        )
    }
}

//Contenido de la pantalla: explicación, campo de correo y botones.
@Composable
fun BodyRecuperarContrasenaScreen(
    correo: String,
    mensaje: String,
    mostrarMensaje: Boolean,
    isLoading: Boolean,
    onCorreoChange: (String) -> Unit,
    enviarButtonPressed: () -> Unit,
    volverButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val linkColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        LogoApp()

        Text(
            text = stringResource(R.string.recuperar_contrasena_titulo),
            fontFamily = BangersFont,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.recuperar_contrasena_texto),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .width(300.dp)
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))
        TextoCorreoRecuperar(
            correo = correo,
            onCorreoChange = onCorreoChange
        )

        Spacer(modifier = Modifier.height(25.dp))
        AppButton(
            stringResource(R.string.enviar_correo),
            primaryLight,
            tertiaryContainerLight,
            onClick = enviarButtonPressed,
            enabled = !isLoading,
            modifier = Modifier
                .height(30.dp)
                .width(280.dp)
                .testTag("enviarCorreoButton")
        )

        if (mostrarMensaje) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = mensaje,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .width(300.dp)
                    .testTag("recuperarMensaje")
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        TextButton(onClick = volverButtonPressed) {
            Text(
                text = stringResource(R.string.volver_iniciar_sesion),
                fontFamily = BangersFont,
                color = linkColor
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun RecuperarContrasenaScreenPreview() {
    RecuperarContrasenaScreen(
        recuperarContrasenaViewModel = viewModel()
    )
}
