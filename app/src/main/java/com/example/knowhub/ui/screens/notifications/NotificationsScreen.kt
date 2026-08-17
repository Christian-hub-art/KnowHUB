package com.example.knowhub.ui.screens.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.notifications.components.CuadroNotificacion
import com.example.knowhub.ui.screens.profile.components.CuadroInformaciónPersonal
import com.example.knowhub.ui.screens.register.BodyRegisterScreen
import com.example.knowhub.ui.screens.register.RegisterScreen
import com.example.knowhub.ui.utils.AppButton
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

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BarraArriba(R.drawable.iconomenu,
                "")

            Spacer(modifier = Modifier.height(35.dp))

            AppButton(
                stringResource(R.string.notificaciones),
                colorResource(R.color.blancoKnowHUB),
                colorResource(R.color.NegroKnowHUB),
                modifier = Modifier
                    .height(40.dp)
                    .width(320.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))
            CuadroNotificacion(
                "comment",
                "Nuevo comentario a tu reseña",
                "9 de agosto de 2026 a las 19:49 \n \" Estoy de acuerdo, pero ... \""
                )
            Spacer(modifier = Modifier.height(20.dp))
            CuadroNotificacion(
                "follower",
                "S4nti4g0 te ha empeado a seguir",
                "9 de agosto de 2026 a las 19:49"
            )
            Spacer(modifier = Modifier.height(20.dp))
            CuadroNotificacion(
                "like",
                "S4nti4g0 le dio \"me gusta\" a tu reseña \n a la clase 3444",
                ""
            )
            Spacer(modifier = Modifier.height(20.dp))
            CuadroNotificacion(
                "like",
                "S4nti4g0 le dio \"me gusta\" \n a tu comentario a la reseña de D4niel",
                ""
            )
            Spacer(modifier = Modifier.height(20.dp))
            CuadroNotificacion(
                "follower",
                "M4ri4 te ha empeado a seguir",
                "9 de agosto de 2026 a las 19:49"
            )
            Spacer(modifier = Modifier.height(20.dp))
            CuadroNotificacion(
                "comment",
                "Nuevo comentario a tu reseña",
                "9 de agosto de 2026 a las 19:49 \n \" No necesariamente ... \""
            )

        }

}

@Composable
@Preview(showBackground = true)
fun NotificationsScreenPreview(){
    NotificatonsScreen()
}
