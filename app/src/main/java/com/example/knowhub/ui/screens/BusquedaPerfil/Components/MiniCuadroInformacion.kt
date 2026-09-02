package com.example.knowhub.ui.screens.BusquedaPerfil.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.knowhub.ui.theme.BangersFont
//Componente individual que muestra una métrica numérica y su respectiva etiqueta descriptiva
@Composable
fun MiniCuadroInformacion(
    tipo: String,
    numero: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            ("$numero"),
            fontFamily = BangersFont,
            fontSize = 22.sp
        )
        // Etiqueta descriptiva de la métrica
        Text(
            text = tipo,
            fontFamily = BangersFont,
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            lineHeight = 9.sp
        )
    }
}

