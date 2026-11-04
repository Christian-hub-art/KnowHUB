package com.example.knowhub.ui.screens.BusquedaFiltro.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.tertiaryContainerLight

//Componente de campo de texto personalizado para ingresar y filtrar datos de búsqueda.
@Composable
fun BarraFiltro(
    filtro: String,
    onFiltroChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    BasicTextField(
        value = filtro,
        onValueChange = onFiltroChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 15.sp,
            fontFamily = BangersFont,
            color = tertiaryContainerLight
        ),
        modifier = modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (filtro.isEmpty()) {
                    Text(
                        text = stringResource(R.string.carrera_nom_materia_idmateria_nom_profesor),
                        fontSize = 12.sp,
                        fontFamily = BangersFont,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        }
    )
}