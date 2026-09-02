package com.example.knowhub.ui.screens.profile


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.ui.screens.login.LoginViewModel
import com.example.knowhub.ui.screens.profile.components.CuadroInformaciónPersonal
import com.example.knowhub.ui.theme.KnowHUBTheme
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.BarraArriba

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = ProfileViewModel(),
    modifier: Modifier = Modifier
) {
    val state by profileViewModel.uiState.collectAsState()
    Box(
        modifier = modifier
    ) {
        BackgroundImage()

        BodyProfileScreen(
            state.nombre,
            state.errorMessageGuardar,
            state.mostrarMensajeErrorGuardar,
            onNombreChange = {profileViewModel.updateNombre(it)},
            guardarBottonPressed = {profileViewModel.guardarBottonPressed()},
            cancelarBottonPressed = {profileViewModel.cancelarBottonPressed()},
            subirfotoBottonPressed = {profileViewModel.subirfotoBottonPressed()},
            eliminarcuentaBottonPressed = {profileViewModel.eliminarcuentaBottonPressed()},
            cambiarcontraseñaBottonPressed = {profileViewModel.cambiarcontraseñaBottonPressed()},
            cambiarCorreoBottonPressed = {profileViewModel.cambiairCorreoBottonPressed()}

        )

    }
}

@Composable
fun BodyProfileScreen(
    nombre: String,
    errorMessageGuardar: String,
    mostrarMensajeErrorGuardar: Boolean,
    onNombreChange: (String) ->Unit,
    guardarBottonPressed: () -> Unit = {},
    cancelarBottonPressed: () -> Unit = {},
    subirfotoBottonPressed: () -> Unit = {},
    eliminarcuentaBottonPressed: () ->Unit = {},
    cambiarcontraseñaBottonPressed: () -> Unit = {},
    cambiarCorreoBottonPressed: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column(
        modifier=modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(17.0F))

        AppLabel(
            stringResource(R.string.perfil),
            colorResource(R.color.blancoKnowHUB),
            colorResource(R.color.NegroKnowHUB),
            modifier = Modifier
                .height(40.dp)
                .width(320.dp)
        )

        Spacer(modifier = Modifier.weight(10.0F))

        Image(
            painter = painterResource(R.drawable.perfil),
            contentDescription = stringResource(R.string.imagen_perfil),
            modifier = Modifier
                .background(
                    colorResource(R.color.blancoKnowHUB)
                )
                .border(
                    1.dp,
                    colorResource(R.color.NegroKnowHUB)
                )
        )
        Spacer(modifier = Modifier.weight(10.0F))

        AppButton(
            stringResource(R.string.subir_foto),
            colorResource(R.color.NegroKnowHUB),
            colorResource(R.color.blancoKnowHUB),
            onClick = {
                subirfotoBottonPressed()
            },
            modifier = Modifier
                .height(30.dp)
                .width(180.dp)

        )

        Spacer(modifier = Modifier.weight(10.0F))

        CuadroInformaciónPersonal(
            nombre,
            onNombreChange = {onNombreChange(it)},
            guardarBottonPressed = guardarBottonPressed,
            cancelarBottonPressed = cancelarBottonPressed,
            cambiarcontraseñaBottonPressed = cambiarcontraseñaBottonPressed,
            cambiarCorreoBottonPressed = cambiarCorreoBottonPressed
        )

        Spacer(modifier = Modifier.weight(0.5F))

        if(mostrarMensajeErrorGuardar){
            Text(errorMessageGuardar)
        }

        Spacer(modifier = Modifier.weight(10.0F))
        AppButton(stringResource(R.string.eliminar_cuenta),
            colorResource(R.color.NegroKnowHUB),
            colorResource(R.color.AmarilloKnowHUB),
            onClick = {
                eliminarcuentaBottonPressed()
            },
            modifier = Modifier
                .height(40.dp)
                .width(180.dp)
                )

        Spacer(modifier = Modifier.weight(20.0F))
    }
}



@Preview
@Composable
fun ProfileScreenPreview() {
        ProfileScreen(
            profileViewModel = viewModel()
        )

}