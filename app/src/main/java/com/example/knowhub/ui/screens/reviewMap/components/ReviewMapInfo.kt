package com.example.knowhub.ui.screens.reviewMap.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.data.Review
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.ProfileAsyncImage
import com.example.knowhub.ui.utils.generarEstrellas

//Tarjeta con el autor, la materia, el profesor y el texto de una reseña del mapa.
@Composable
fun ReviewMapInfo(
    review: Review,
    isDarkMode: Boolean,
    onDismiss: () -> Unit,
    onOpenReview: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
                .background(REVIEW_HEADER_BLUE)
                .padding(horizontal = 14.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileAsyncImage(
                profileImage = review.fotoAutor,
                size = 42,
                modifier = Modifier.border(2.dp, Color.White, CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = review.nombreEstudiante,
                    color = Color.White,
                    fontFamily = BangersFont,
                    fontSize = 18.sp
                )
                Text(
                    text = review.nombreAsignatura,
                    color = Color.White.copy(alpha = 0.85f),
                    fontFamily = ArvoFont,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                Text("×", color = Color.White, fontSize = 26.sp)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.mapa_profesor, review.nombreProfesor),
                    color = panelTextColor(isDarkMode),
                    fontFamily = ArvoFont,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(text = generarEstrellas(review.calificacion), fontSize = 15.sp)
            }
            Text(
                text = review.descripcion,
                color = panelTextColor(isDarkMode),
                fontFamily = ArvoFont,
                fontSize = 12.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            AppButton(
                textoButon = stringResource(R.string.mapa_ver_resena),
                colorTexto = primaryLight,
                colorBoton = primaryContainerLight,
                onClick = onOpenReview,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
            )
        }
    }
}
