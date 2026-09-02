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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight

//Sección de encabezado que muestra la imagen de perfil del usuario y sus estadísticas
@Composable
fun InformacionPerfil(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.width(350.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Marco contenedor de la foto de perfil
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
            Icon(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = "Foto Perfil",
                modifier = Modifier.size(65.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        // Bloque con las métricas del usuario
        CuadrosInformacionPerfil(
            seguidos = 48,
            materiasResenadas = 32,
            seguidores = 126,
            modifier = Modifier
                .weight(1f)
        )
    }
}