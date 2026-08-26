package com.example.knowhub.ui.screens.BusquedaPerfil.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CuadrosInformacionPerfil(
    seguidos: Int,
    materiasResenadas: Int,
    seguidores: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        MiniCuadroInformacion(
            tipo = "seguidos",
            numero = seguidos,
            modifier = Modifier.weight(1f)
        )

        MiniCuadroInformacion(
            tipo = "Materias\nreseñadas",
            numero = materiasResenadas,
            modifier = Modifier.weight(1f)
        )

        MiniCuadroInformacion(
            tipo = "Seguidores",
            numero = seguidores,
            modifier = Modifier.weight(1f)
        )
    }
}