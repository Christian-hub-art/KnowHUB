package com.example.knowhub.ui.screens.BusquedaPerfil.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.knowhub.R

//Contenedor horizontal para las estadísticas del perfil del usuario.

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
        // Bloque de métrica para usuarios seguidos
        MiniCuadroInformacion(
            tipo = stringResource(R.string.seguidos),
            numero = seguidos,
            modifier = Modifier.weight(1f)
        )

        // Bloque de métrica para materias evaluadas
        MiniCuadroInformacion(
            tipo = stringResource(R.string.materias_resenadas),
            numero = materiasResenadas,
            modifier = Modifier.weight(1f)
        )

        // Bloque de métrica para seguidores
        MiniCuadroInformacion(
            tipo = stringResource(R.string.seguidores),
            numero = seguidores,
            modifier = Modifier.weight(1f)
        )
    }
}