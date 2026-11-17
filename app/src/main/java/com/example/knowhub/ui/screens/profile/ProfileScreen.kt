package com.example.knowhub.ui.screens.profile


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import com.example.knowhub.ui.screens.BusquedaPerfil.Components.CuadrosInformacionPerfil
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.ui.screens.profile.components.CuadroInformaciónPersonal
import com.example.knowhub.ui.screens.profile.components.PickImageButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.ProfileAsyncImage


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    seguidoresPressed: (String) -> Unit = {},
    seguidosPressed: (String) -> Unit = {},
    modificarDatosPressed: () -> Unit = {},
    iniciarSesionPressed: () -> Unit = {},
    registrarsePressed: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    // Carga el perfil al entrar y al volver de Editar perfil
    LaunchedEffect(Unit) {
        viewModel.getUser()
        viewModel.getUserReviews()
    }

    Box(
        modifier = modifier
    ) {
        BackgroundImage()

        BodyProfileScreen(
            viewModel,
            state.nombre,
            state.profileImageUrl,
            state.errorMessageFoto,
            state.mostrarMensajeErrorFoto,
            state.isUploadingFoto,
            state.errorMessageCarga,
            state.mostrarMensajeErrorCarga,
            correo = state.correo,
            seguidores = state.seguidores,
            seguidos = state.seguidos,
            materiasResenadas = state.materiasResenadas,
            seguidoresPressed = { seguidoresPressed(state.userId) },
            seguidosPressed = { seguidosPressed(state.userId) },
            modificarDatosPressed = modificarDatosPressed,
            eliminarcuentaBottonPressed = {viewModel.eliminarcuentaBottonPressed()},
            isAuthenticated = state.isAuthenticated,
            iniciarSesionPressed = iniciarSesionPressed,
            registrarsePressed = registrarsePressed
        )

    }
}

@Composable
fun BodyProfileScreen(
    profileViewModel: ProfileViewModel,
    nombre: String,
    profileImage: String?,
    errorMessageFoto: String,
    mostrarMensajeErrorFoto: Boolean,
    isUploadingFoto: Boolean,
    errorMessageCarga: String,
    mostrarMensajeErrorCarga: Boolean,
    correo: String = "",
    seguidores: Int = 0,
    seguidos: Int = 0,
    materiasResenadas: Int = 0,
    seguidoresPressed: () -> Unit = {},
    seguidosPressed: () -> Unit = {},
    modificarDatosPressed: () -> Unit = {},
    eliminarcuentaBottonPressed: () -> Unit = {},
    isAuthenticated: Boolean = true,
    iniciarSesionPressed: () -> Unit = {},
    registrarsePressed: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppLabel(
            stringResource(R.string.perfil),
            colorResource(R.color.blancoKnowHUB),
            colorResource(R.color.NegroKnowHUB),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(104.dp)) {
                ProfileAsyncImage(
                    profileImage = profileImage,
                    size = 104,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                PickImageButton(
                    action = { profileViewModel.uploadImageToFirebase(it) },
                    isUploading = isUploadingFoto,
                    modifier = Modifier.align(Alignment.BottomEnd).size(36.dp)
                )
            }

            CuadrosInformacionPerfil(
                seguidos = seguidos,
                materiasResenadas = materiasResenadas,
                seguidores = seguidores,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                onSeguidosClick = seguidosPressed,
                onSeguidoresClick = seguidoresPressed
            )
        }

        if (mostrarMensajeErrorFoto) {
            Text(
                errorMessageFoto,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else if (isUploadingFoto) {
            Text(
                stringResource(R.string.profile_photo_uploading),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(28.dp))
        CuadroInformaciónPersonal(
            nombre = nombre,
            correo = correo,
            isAuthenticated = isAuthenticated,
            modificarDatosPressed = modificarDatosPressed,
            eliminarCuentaPressed = eliminarcuentaBottonPressed,
            iniciarSesionPressed = iniciarSesionPressed,
            registrarsePressed = registrarsePressed
        )

        if (mostrarMensajeErrorCarga) {
            Text(errorMessageCarga, color = MaterialTheme.colorScheme.error)
        }

    }
}





@Preview
@Composable
fun ProfileScreenPreview() {
        ProfileScreen(
            viewModel = viewModel()
        )

}