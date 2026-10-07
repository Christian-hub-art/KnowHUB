package com.example.knowhub.ui.screens.BusquedaPerfil

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
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
import com.example.knowhub.R
import com.example.knowhub.data.Review
import com.example.knowhub.data.Usuario
import com.example.knowhub.ui.screens.BusquedaPerfil.Components.InformacionPerfil
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.AppLabelBig
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.CajaBusqueda

/**
 * Pantalla del perfil buscado.
 * Conecta el estado del ViewModel con la interfaz gráfica.
 */
@Composable
fun BusquedaPerfilScreen(
    userId: String,
    busquedaPerfilViewModel: BusquedaPerfilViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by busquedaPerfilViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        busquedaPerfilViewModel.getUserReviews(userId)
        busquedaPerfilViewModel.getUserProfile(userId)
    }

    Box(
        modifier = modifier
    ) {
        BackgroundImage()

        BodyBusquedaPerfilScrenn(
            user = uiState.user,
            reviews = uiState.reviews,
            onClick = {}
        )
    }
}

/**
 * Contenido principal de la pantalla de perfil y sus reseñas asociadas.
 */
@Composable
fun BodyBusquedaPerfilScrenn(
    user: Usuario = Usuario(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    ),
    reviews: List<Review> = emptyList(),
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
                user.nombreUsuario,
                colorTexto = primaryLight,
                color = tertiaryContainerLight,
                modifier = Modifier
                    .width(350.dp)
                    .height(45.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Componente con imagen y estadísticas del perfil
            InformacionPerfil(
                user = user
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Fila con la carrera, ícono y botón de seguimiento
            Row(
                modifier = Modifier.width(320.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Filled.School,
                    contentDescription = stringResource(R.string.icono_graduado),
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    stringResource(R.string.ingenieria_sistemas),
                    fontFamily = BangersFont
                )

                Spacer(modifier = Modifier.weight(1f))

                AppButton(
                    stringResource(R.string.btn_seguir),
                    colorBoton = primaryContainerLight,
                    onClick = onClick
                )
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

        // Listado dinámico de las reseñas publicadas por el perfil
        items(reviews) { review ->

            CajaBusqueda(
                review,
                {
                    // TODO: acción al hacer click
                },
                modifier = Modifier.width(350.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Preview
@Composable
fun BusquedaPerfilScreenReview() {
    BodyBusquedaPerfilScrenn()
}