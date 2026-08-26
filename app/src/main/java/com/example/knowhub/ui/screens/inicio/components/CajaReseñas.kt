package com.example.knowhub.ui.screens.inicio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.data.MateriaResumida
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight

@Composable
fun CajaReseñas(
    temaGeneral: String,
    materias: List<MateriaResumida>,
    colorTexto: Color,
    colorCaja: Color,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(horizontal = 16.dp)
            .drawBehind {
                drawRect(
                    color = colorCaja,
                    topLeft = Offset(5.dp.toPx(), 5.dp.toPx()),
                    size = size
                )
            }
            .border(2.dp, tertiaryContainerLight)
            .background(primaryLight)
    ) {
        Row(
            modifier = Modifier
                .background(colorCaja)
                .fillMaxWidth()
                .border(2.dp, tertiaryContainerLight)
                .padding(horizontal = 12.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = temaGeneral,
                color = colorTexto,
                fontFamily = BangersFont
            )
            TextButton(
                onClick = onSeeAllClick,
                modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = "Ver más ➔",
                    color = colorTexto,
                    fontFamily = BangersFont
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            items(items = materias) { materia ->
                Materia(
                    calificacion = materia.calificacion,
                    nombreMateria = materia.nombreMateria,
                    profesor = materia.profesor,
                    numeroResenas = materia.numeroResenas,
                )
            }
        }
    }
}

@Composable
@Preview
fun CajaReseñasPreview() {
    val listaMateriasPrueba = listOf(
        MateriaResumida(
            calificacion = 5,
            nombreMateria = "Cálculo I",
            profesor = "Angarita",
            numeroResenas = 14
        )
    )
}

