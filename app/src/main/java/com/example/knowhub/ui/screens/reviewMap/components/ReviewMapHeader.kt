package com.example.knowhub.ui.screens.reviewMap.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight

//Encabezado del mapa con el título, el total de reseñas, el modo oscuro y el botón de actualizar.
@Composable
fun ReviewMapHeader(
    totalReviews: Int,
    isLoading: Boolean,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onRefresh: () -> Unit
) {
    Surface(
        color = panelColor(isDarkMode),
        contentColor = panelTextColor(isDarkMode),
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 8.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryContainerLight)
                    .padding(start = 16.dp, end = 8.dp, top = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.mapa_titulo),
                        color = primaryLight,
                        fontFamily = BangersFont,
                        fontSize = 23.sp
                    )
                    Text(
                        text = stringResource(R.string.mapa_subtitulo),
                        color = primaryLight,
                        fontFamily = ArvoFont,
                        fontSize = 11.sp
                    )
                }
                Text(
                    text = "$totalReviews",
                    modifier = Modifier
                        .background(secondaryContainerLight, CircleShape)
                        .padding(horizontal = 11.dp, vertical = 5.dp),
                    color = tertiaryContainerLight,
                    fontFamily = BangersFont,
                    fontSize = 18.sp
                )
                IconButton(
                    onClick = onToggleDarkMode,
                    modifier = Modifier.size(42.dp)
                ) {
                    Icon(
                        imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = stringResource(
                            if (isDarkMode) R.string.mapa_modo_claro else R.string.mapa_modo_oscuro
                        ),
                        tint = if (isDarkMode) Color(0xFFFFD166) else primaryLight
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 6.dp, top = 2.dp, bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.mapa_creditos),
                    color = panelTextColor(isDarkMode).copy(alpha = 0.65f),
                    fontFamily = ArvoFont,
                    fontSize = 9.sp
                )
                IconButton(
                    onClick = onRefresh,
                    enabled = !isLoading,
                    modifier = Modifier.size(38.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = if (isDarkMode) Color(0xFF8BB8FF) else primaryContainerLight,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.mapa_actualizar),
                            tint = if (isDarkMode) Color(0xFF8BB8FF) else primaryContainerLight,
                            modifier = Modifier.size(21.dp)
                        )
                    }
                }
            }
        }
    }
}
