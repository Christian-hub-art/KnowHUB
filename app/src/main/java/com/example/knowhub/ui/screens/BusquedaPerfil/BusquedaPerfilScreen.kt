package com.example.knowhub.ui.screens.BusquedaPerfil

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.data.Review
import com.example.knowhub.ui.screens.BusquedaPerfil.Components.InformacionPerfil
import com.example.knowhub.ui.screens.completeReviews.components.Review as ReviewComponent
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.AppLabelBig
import com.example.knowhub.ui.utils.BackgroundImage

/**
 * Pantalla del perfil de otro usuario.
 * Recibe el userId por la ruta y carga sus datos y reseñas desde Firestore.
 */
@Composable
fun BusquedaPerfilScreen(
    userId: String,
    modifier: Modifier = Modifier,
    viewModel: BusquedaPerfilViewModel = viewModel(),
    reviewPressed: (String) -> Unit = {}
){
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getUserProfile(userId)
        viewModel.getUserReviews(userId)
    }

    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyBusquedaPerfilScrenn(
            userName = uiState.userName,
            fotoPerfil = uiState.fotoPerfil,
            reviews = uiState.reviews,
            errorMessage = uiState.errorMessage,
            mostrarMensajeError = uiState.mostrarMensajeError,
            reviewPressed = reviewPressed,
            followed = uiState.followed,
            seguidores = uiState.seguidores,
            seguidos = uiState.seguidos,
            mostrarSeguir = userId != uiState.currentUserId,
            onClick = { viewModel.followOrUnfollowUser(userId) }
        )
    }
}
//Contenido principal de la pantalla de perfil y sus reseñas asociadas.
@Composable
fun BodyBusquedaPerfilScrenn(
    userName: String,
    fotoPerfil: String?,
    reviews: List<Review>,
    errorMessage: String = "",
    mostrarMensajeError: Boolean = false,
    reviewPressed: (String) -> Unit = {},
    followed: Boolean = false,
    seguidores: Int = 0,
    seguidos: Int = 0,
    mostrarSeguir: Boolean = true,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            Spacer(modifier = Modifier.height(20.dp))
            // Nombre del perfil del usuario
            AppLabelBig(
                userName,
                colorTexto = primaryLight,
                color = tertiaryContainerLight,
                modifier = Modifier
                    .width(350.dp)
                    .height(45.dp)
            )

            if (mostrarMensajeError) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(errorMessage)
            }

            Spacer(modifier = Modifier.height(40.dp))
            InformacionPerfil(
                fotoPerfil = fotoPerfil,
                seguidos = seguidos,
                materiasResenadas = reviews.map { it.nombreAsignatura }.distinct().size,
                seguidores = seguidores
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.width(320.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                if (mostrarSeguir) {
                    AppButton(
                        stringResource(if (followed) R.string.btn_siguiendo else R.string.btn_seguir),
                        colorBoton = primaryContainerLight,
                        onClick = onClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            // Encabezado de la lista de reseñas
            AppLabel(
                stringResource(R.string.rese_as_publicadas),
                colorTexto = tertiaryContainerLight,
                color = primaryLight,
                modifier = Modifier.width(350.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        items(reviews) { review ->

            ReviewComponent(
                review,
                reviewPressed = reviewPressed,
                modifier = Modifier.width(350.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
@Preview
fun BusquedaPerfilScreenReview(){
    BusquedaPerfilScreen(userId = "")
}
