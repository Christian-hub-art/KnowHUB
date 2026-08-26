package com.example.knowhub.ui.screens.completeReviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton

import com.example.knowhub.ui.utils.generarEstrellas


@Composable
fun Review(
    Fecha: String,
    Estudiante: String,
    Calificacion: Int,
    Reseña: String,
    Likes: Int,
    cantidadComentarios: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row() {
            Column() {
                Text(
                    text = Estudiante,
                    fontSize = 17.sp,
                    fontFamily = BangersFont
                )
                Text(
                    text = Fecha,
                    fontSize = 15.sp,
                    fontFamily = BangersFont
                )
            }
            Spacer(modifier = Modifier.width(20.dp))

            val estrellas= generarEstrellas(Calificacion)
            Text(
                text = estrellas,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    primaryLight
                )
                .border(
                    4.dp,
                    tertiaryContainerLight ,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {

            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                Spacer(modifier = Modifier.height(6.dp))


                Text(
                    text = "\"" + Reseña + "\"",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(20.dp))
                Row( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,) {
                        IconButton(
                            onClick = { /* Acción de Like */ },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.iconolike),
                                contentDescription = "Icono",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "( "+ Likes + " )",
                            fontSize = 17.sp,
                            fontFamily = BangersFont
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically,) {
                        IconButton(
                            onClick = { /* Acción de Like */ },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.iconocomentario),
                                contentDescription = "Icono",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "( " + cantidadComentarios + " )",
                            fontSize = 17.sp,
                            fontFamily = BangersFont
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}


@Composable
@Preview
fun ReviewPreview(){
    Review(
        "15 Nov 2026",
        "Dana Trujillo",
        4,
        "Explica super y califica suave!!!",
        5,
        5
    )
}