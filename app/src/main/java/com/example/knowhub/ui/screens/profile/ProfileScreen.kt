package com.example.knowhub.ui.screens.profile


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.ui.screens.profile.components.CuadroInformaciónPersonal
import com.example.knowhub.ui.screens.profile.components.PickImageButton
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.ProfileAsyncImage

//Pantalla principal del perfil de usuario con opciones de actualización e información personal.
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    Box(
        modifier = modifier
    ) {
        BackgroundImage()

        BodyProfileScreen(
            viewModel,
            state.nombre,
            state.errorMessageGuardar,
            state.mostrarMensajeErrorGuardar,
            state.profileImageUrl,
            state.errorMessageFoto,
            state.mostrarMensajeErrorFoto,
            state.fotoActualizada,
            state.estaSubiendoFoto,
            onNombreChange = {viewModel.updateNombre(it)},
            guardarBottonPressed = {viewModel.guardarBottonPressed()},
            cancelarBottonPressed = {viewModel.cancelarBottonPressed()},
            eliminarcuentaBottonPressed = {viewModel.eliminarcuentaBottonPressed()},
            cambiarcontraseñaBottonPressed = {viewModel.cambiarcontraseñaBottonPressed()},
            cambiarCorreoBottonPressed = {viewModel.cambiairCorreoBottonPressed()}

        )

    }
}
//Contenido principal y maquetación de la pantalla de perfil.
@Composable
fun BodyProfileScreen(
    profileViewModel: ProfileViewModel,
    nombre: String,
    errorMessageGuardar: String,
    mostrarMensajeErrorGuardar: Boolean,
    profileImage: String?,
    errorMessageFoto: String = "",
    mostrarMensajeErrorFoto: Boolean = false,
    fotoActualizada: Boolean = false,
    estaSubiendoFoto: Boolean = false,
    onNombreChange: (String) -> Unit,
    guardarBottonPressed: () -> Unit = {},
    cancelarBottonPressed: () -> Unit = {},
    eliminarcuentaBottonPressed: () -> Unit = {},
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

        ProfileAsyncImage(
            profileImage = profileImage,
            size = 200
        )

        Spacer(modifier = Modifier.weight(10.0F))

        PickImageButton(
            action = {
                profileViewModel.uploadImageToFirebase(it)
            }
        )

        if (estaSubiendoFoto) {
            Text(stringResource(R.string.subiendo_foto))
        }
        if (mostrarMensajeErrorFoto) {
            Text(
                text = errorMessageFoto,
                color = if (fotoActualizada) Color(0xFF2E7D32) else Color.Red
            )
        }

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
            viewModel = viewModel()
        )

}
