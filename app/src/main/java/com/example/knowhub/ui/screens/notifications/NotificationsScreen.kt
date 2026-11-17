package com.example.knowhub.ui.screens.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.data.Notificacion
import com.example.knowhub.ui.screens.notifications.components.CuadroNotificacion
import com.example.knowhub.ui.screens.profile.components.CuadroInformaciónPersonal
import com.example.knowhub.ui.screens.register.BodyRegisterScreen
import com.example.knowhub.ui.screens.register.RegisterScreen
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.BarraArriba
//Pantalla principal que muestra el listado de notificaciones recibidas por el usuario.
@Composable
fun NotificatonsScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel = viewModel(),
    reviewPressed: (String) -> Unit = {},
    perfilPressed: (String) -> Unit = {}
){
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier){
        BackgroundImage()
        BodyNotificationsScreen(
            notifications = uiState.notifications,
            errorMessage = uiState.errorMessage,
            notificationPressed = { notificacion ->
                // Seguidor: abre su perfil. Like o comentario: abre la reseña
                if (notificacion.tipo == "follower" && notificacion.userId.isNotEmpty()) {
                    perfilPressed(notificacion.userId)
                } else if (notificacion.reviewId.isNotEmpty()) {
                    reviewPressed(notificacion.reviewId)
                }
            }
        )
    }
}
//Disposición del contenido visual y lista de elementos para las notificaciones.
@Composable
fun BodyNotificationsScreen(
    modifier: Modifier = Modifier,
    notifications: List<Notificacion> = emptyList(),
    errorMessage: String? = null,
    notificationPressed: (Notificacion) -> Unit = {}
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item (){
                Spacer(modifier = Modifier.height(35.dp))

                AppLabel(
                    stringResource(R.string.notificaciones),
                    colorTexto = if (isSystemInDarkTheme()) Color.White else primaryLight,
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.surfaceContainerHighest
                    } else {
                        tertiaryContainerLight
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(320.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                if (errorMessage != null) {
                    Text(errorMessage)
                    Spacer(modifier = Modifier.height(15.dp))
                } else if (notifications.isEmpty()) {
                    Text(stringResource(R.string.sin_notificaciones))
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
            items(notifications.size){ index ->
                CuadroNotificacion(
                    infoNotificacion = notifications[index],
                    onClick = { notificationPressed(notifications[index]) }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NotificationsScreenPreview(){
    NotificatonsScreen()
}