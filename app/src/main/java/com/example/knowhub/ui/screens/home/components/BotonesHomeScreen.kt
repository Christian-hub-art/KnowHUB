package com.example.knowhub.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.home.components.IconoImagen

@Composable
fun BotonesHomeScreen(
    modifier : Modifier = Modifier
){
    Row(){
        IconoImagen(R.drawable.perfil, stringResource(R.string.imagen_perfil),
            stringResource(R.string.perfil), modifier = Modifier.background(colorResource(R.color.blancoKnowHUB))
        )
        IconoImagen(R.drawable.resena, stringResource(R.string.imagen_rese_a),
            stringResource(R.string.rese_a), modifier = Modifier.background(colorResource(R.color.blancoKnowHUB))
        )
    }
    Spacer(modifier = Modifier.padding(8.dp))
    Row(){
        IconoImagen(R.drawable.resenabus, stringResource(R.string.imagen_buscar),
            stringResource(R.string.buscar_rese_as), modifier = Modifier.background(colorResource(R.color.blancoKnowHUB))
        )
        IconoImagen(R.drawable.notificacion, stringResource(R.string.imagen_notificaci_n),
            stringResource(R.string.notificaci_n), modifier = Modifier.background(colorResource(R.color.blancoKnowHUB))
        )
    }
}