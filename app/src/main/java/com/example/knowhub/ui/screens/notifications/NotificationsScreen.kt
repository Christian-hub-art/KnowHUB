package com.example.knowhub.ui.screens.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.data.local.LocalNotificacionProvider
import com.example.knowhub.ui.screens.notifications.components.CuadroNotificacion
import com.example.knowhub.ui.screens.profile.components.CuadroInformaciónPersonal
import com.example.knowhub.ui.screens.register.BodyRegisterScreen
import com.example.knowhub.ui.screens.register.RegisterScreen
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.BarraArriba

@Composable
fun NotificatonsScreen(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier){
        BackgroundImage()
        BodyNotificationsScreen()
    }
}

@Composable
fun BodyNotificationsScreen(
    modifier: Modifier = Modifier
){
        val allNotifications = LocalNotificacionProvider.notificaciones

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
                }
                items(allNotifications.size){ index ->
                    CuadroNotificacion(
                        infoNotificacion = allNotifications[index]
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
