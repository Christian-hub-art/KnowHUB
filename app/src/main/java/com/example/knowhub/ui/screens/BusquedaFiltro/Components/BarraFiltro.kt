package com.example.knowhub.ui.screens.BusquedaFiltro.Components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight

@Composable
fun BarraFiltro(
    filtro: String,
    onFiltroChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = filtro,
        onValueChange = onFiltroChange,
        label = { Text(
            stringResource(R.string.carrera_nom_materia_idmateria_nom_profesor),
            fontSize = 11.sp)
                },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = primaryLight,
            focusedContainerColor = primaryLight,
            focusedBorderColor = tertiaryContainerLight,
            unfocusedBorderColor = tertiaryContainerLight
        ),
        modifier = Modifier
            .height(30.dp)
            .width(280.dp)
    )
}