package com.example.knowhub.ui.screens.completeSpecificReview.components
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.generarEstrellas

//Componente que muestra un comentario individual realizado dentro de una reseña específica.
@Composable
fun Comment(
    Fecha: String,
    Estudiante: String,
    Comentario: String,
    Likes: Int,
    cantidadComentarios: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    mostrarEliminar: Boolean = false,
    onEliminarClick: () -> Unit = {},
    likedByCurrentUser: Boolean = false,
    onLikeClick: () -> Unit = {},
    likeEnabled: Boolean = true
) {
    Column(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .clip(RoundedCornerShape(16.dp))
                .background(
                    primaryLight
                )
                .border(
                    4.dp,
                    tertiaryContainerLight ,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {// Encabezado con el nombre del estudiante y fecha
            Row(modifier = Modifier.padding(10.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

            Text(
                text = Estudiante,
                fontSize = 17.sp,
                fontFamily = BangersFont
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = Fecha,
                    fontSize = 17.sp,
                    fontFamily = BangersFont
                )
                if (mostrarEliminar) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "🗑",
                        fontSize = 20.sp,
                        modifier = Modifier.clickable { onEliminarClick() }
                    )
                }
            }

            }
            // Cuerpo del comentario y métricas de interacción
            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                Spacer(modifier = Modifier.height(6.dp))


                Text(
                    text = "\"" + Comentario + "\"",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(20.dp))
                Row( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {// Reacción: Me gusta
                    Row(verticalAlignment = Alignment.CenterVertically,) {
                        IconButton(
                            onClick = onLikeClick,
                            enabled = likeEnabled,
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.iconolike),
                                contentDescription = if (likedByCurrentUser) {
                                    "Quitar me gusta"
                                } else {
                                    "Dar me gusta"
                                },
                                modifier = Modifier.fillMaxSize(),
                                tint = if (likedByCurrentUser) Color(0xFFE53935) else Color.Unspecified
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "( "+ Likes + " )",
                            fontSize = 17.sp,
                            fontFamily = BangersFont
                        )
                    } // Reacción: Respuestas / Comentarios
                    Row(verticalAlignment = Alignment.CenterVertically,) {
                        IconButton(
                            onClick = onClick,
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
fun CommentPreview(){
    Comment(
        "15 Nov 2026",
        "Dana Trujillo",
        "Explica super y califica suave!!!",
        4,
        10,
    )
}