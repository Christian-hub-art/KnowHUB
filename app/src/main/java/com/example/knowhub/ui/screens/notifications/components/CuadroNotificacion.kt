package com.example.knowhub.ui.screens.notifications.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.data.Notificacion
import com.example.knowhub.data.local.LocalNotificacionProvider
import com.example.knowhub.ui.screens.notifications.NotificatonsScreen
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton

import com.example.knowhub.ui.utils.CuadroTexto



@Composable
fun CuadroNotificacion(
    infoNotificacion: Notificacion,
    modifier: Modifier = Modifier
) {

    val colorSombra = tertiaryContainerLight
    val colorNotificacion: Color
    val idImagen: Int

    when(infoNotificacion.tipo){
        "follower" -> {
            colorNotificacion = secondaryContainerLight
            idImagen= R.drawable.iconofollower
        }
        "comment" -> {
            colorNotificacion = secondaryLight
            idImagen= R.drawable.iconocomentario
        }
        "like" -> {
            colorNotificacion = tertiaryLight
            idImagen= R.drawable.iconolike
        }
        //Caso default, tipo de notificacion invalido
        else -> {
            colorNotificacion = tertiaryContainerLight
            idImagen= R.drawable.iconodesconocido
        }
    }


    Box(
        modifier = modifier
            .drawBehind {
                drawRect(
                    color = colorSombra,
                    topLeft = Offset(
                        5.dp.toPx(),
                        5.dp.toPx()
                    ),
                    size = size
                )
            }
            .background(primaryLight)
            .border(
                2.dp,
                tertiaryContainerLight
            )
            .height(80.dp)
            .width(360.dp)
    ) {

        Row(modifier = modifier) {
            Box(
                modifier = modifier
                    .size(20.dp, 100.dp)
                    .background(colorNotificacion)
                    .border(2.dp, Color.Black)
            )

            Spacer(modifier = Modifier.weight(1.0F))

            Column(verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = infoNotificacion.titulo,
                    textAlign = TextAlign.Left,
                    fontFamily = ArvoFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = infoNotificacion.descripcion,
                    textAlign = TextAlign.Left,
                    fontFamily = ArvoFont,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.weight(6F))

            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ){
                IconoNotificacion(idImagen,colorNotificacion)
            }

            Spacer(modifier = Modifier.weight(1.0F))
        }
    }
}

@Composable
@Preview
fun CuadroNotificacionPreview(){
    CuadroNotificacion(LocalNotificacionProvider.notificaciones[9])
}