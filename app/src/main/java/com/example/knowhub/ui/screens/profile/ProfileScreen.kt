package com.example.knowhub.ui.screens.profile


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import com.example.knowhub.ui.screens.BusquedaPerfil.Components.CuadrosInformacionPerfil
import com.example.knowhub.ui.theme.ArvoFont
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
            state.errorMessageCarga,
            state.mostrarMensajeErrorCarga,
            name = state.name,
            correo = state.correo,
            seguidores = state.seguidores,
            seguidos = state.seguidos,
            materiasResenadas = state.materiasResenadas,
            onNombreChange = {viewModel.updateNombre(it)},
            guardarBottonPressed = {viewModel.guardarBottonPressed()},
            eliminarcuentaBottonPressed = {viewModel.eliminarcuentaBottonPressed()},
            cambiarcontraseñaBottonPressed = {viewModel.cambiarcontraseñaBottonPressed()},
            cambiarCorreoBottonPressed = {viewModel.cambiairCorreoBottonPressed()}

        )

    }
}

@Composable
fun BodyProfileScreen(
    profileViewModel: ProfileViewModel,
    nombre: String,
    errorMessageGuardar: String,
    mostrarMensajeErrorGuardar: Boolean,
    profileImage: String?,
    errorMessageCarga: String,
    mostrarMensajeErrorCarga: Boolean,
    name: String = "",
    correo: String = "",
    seguidores: Int = 0,
    seguidos: Int = 0,
    materiasResenadas: Int = 0,
    onNombreChange: (String) -> Unit,
    guardarBottonPressed: () -> Unit = {},
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
            size = 150
        )

        Spacer(modifier = Modifier.weight(5.0F))

        Text(
            name,
            fontFamily = ArvoFont,
            fontSize = 18.sp
        )
        Text(
            correo,
            fontFamily = ArvoFont,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.weight(5.0F))

        CuadrosInformacionPerfil(
            seguidos = seguidos,
            materiasResenadas = materiasResenadas,
            seguidores = seguidores,
            modifier = Modifier.width(320.dp)
        )

        Spacer(modifier = Modifier.weight(10.0F))

        PickImageButton(
            action = {
                profileViewModel.uploadImageToFirebase(it)
            }
        )

        Spacer(modifier = Modifier.weight(10.0F))

        CuadroInformaciónPersonal(
            nombre,
            onNombreChange = {onNombreChange(it)},
            guardarBottonPressed = guardarBottonPressed,
            cambiarcontraseñaBottonPressed = cambiarcontraseñaBottonPressed,
            cambiarCorreoBottonPressed = cambiarCorreoBottonPressed
        )

        Spacer(modifier = Modifier.weight(0.5F))

        if(mostrarMensajeErrorCarga){
            Text(errorMessageCarga)
        }

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