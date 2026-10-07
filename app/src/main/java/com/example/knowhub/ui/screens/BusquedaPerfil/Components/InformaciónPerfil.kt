package com.example.knowhub.ui.screens.BusquedaPerfil.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.knowhub.R
import com.example.knowhub.data.Usuario
import com.example.knowhub.ui.theme.primaryLight

@Composable
fun InformacionPerfil(
    user: Usuario,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.width(350.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(85.dp)
                .background(primaryLight)
                .border(
                    width = 2.dp,
                    color = Color.Black
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = user.foto,
                contentDescription = "Foto de perfil",
                modifier = Modifier.size(65.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        CuadrosInformacionPerfil(
            seguidos = user.siguiendo.toIntOrNull() ?: 0,
            materiasResenadas = user.likes.toIntOrNull() ?: 0,
            seguidores = user.seguidores.toIntOrNull() ?: 0,
            modifier = Modifier.weight(1f)
        )
    }
}
