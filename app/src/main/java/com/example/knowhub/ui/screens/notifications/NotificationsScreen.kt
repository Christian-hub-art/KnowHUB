package com.example.knowhub.ui.screens.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.knowhub.R
import com.example.knowhub.data.Notificacion
import com.example.knowhub.ui.screens.notifications.components.CuadroNotificacion
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
//Pantalla principal que muestra el listado de notificaciones recibidas por el usuario.
@Composable
fun NotificatonsScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier){
        BackgroundImage()
        BodyNotificationsScreen(
            notifications = uiState.notifications,
            errorMessage = uiState.errorMessage.orEmpty()
        )
    }
}
//Disposición del contenido visual y lista de elementos para las notificaciones.
@Composable
fun BodyNotificationsScreen(
    modifier: Modifier = Modifier,
    notifications: List<Notificacion> = emptyList(),
    errorMessage: String = ""
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {

            item (){
                Spacer(modifier = Modifier.height(35.dp))

                AppLabel(
                    stringResource(R.string.notificaciones),
                    primaryLight,
                    tertiaryContainerLight,
                    modifier = Modifier
                        .height(40.dp)
                        .width(320.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))
                if (errorMessage.isNotBlank()) {
                    androidx.compose.material3.Text(errorMessage)
                }
            }
            items(notifications.size){ index ->
                CuadroNotificacion(
                    infoNotificacion = notifications[index]
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