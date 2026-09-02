package com.example.knowhub.ui.screens.completeSpecificReview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.data.Review
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.generarEstrellas

//Componente que muestra la tarjeta detallada de una reseña específica incluyendo el profesor y la asignatura.
@Composable
fun Review(
    review: Review,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {// Encabezado superior con información de profesor y asignatura
        Row(modifier = Modifier.padding(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "👨‍🏫 " + review.nombreProfesor,
                fontSize = 17.sp,
                fontFamily = BangersFont
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = review.nombreAsignatura,
                fontSize = 17.sp,
                fontFamily = BangersFont
            )
        }
        // Tarjeta contenedora de la reseña
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
        ) {// Fila con datos del autor y calificación
            Row(modifier = Modifier.padding(10.dp)) {
                Column() {
                    Text(
                        text = review.nombreEstudiante,
                        fontSize = 17.sp,
                        fontFamily = BangersFont
                    )
                    Text(
                        text = review.fechaPublicacion,
                        fontSize = 15.sp,
                        fontFamily = BangersFont
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))

                val estrellas= generarEstrellas(review.calificacion)
                Text(
                    text = estrellas,
                    fontSize = 18.sp
                )
            }
            // Cuerpo descriptivo y contadores de interacción
            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                Spacer(modifier = Modifier.height(6.dp))


                Text(
                    text = "\"" + review.descripcion + "\"",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(20.dp))
                Row( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {// Botón de Me gusta
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
                            text = "( "+ review.likes + " )",
                            fontSize = 17.sp,
                            fontFamily = BangersFont
                        )
                    }// Botón de Comentarios
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
                            text = "( " + review.cantidadComentarios + " )",
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