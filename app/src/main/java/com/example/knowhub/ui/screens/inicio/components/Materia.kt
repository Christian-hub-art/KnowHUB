package com.example.knowhub.ui.screens.inicio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.generarEstrellas
import com.example.knowhub.R

import androidx.compose.ui.platform.testTag

@Composable
fun Materia(
    calificacion: Int,
    nombreMateria: String,
    profesor: String,
    numeroResenas: Int,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val outlineColor = if (isDarkTheme) MaterialTheme.colorScheme.outline else Color.Black
    val cardBackground = if (isDarkTheme) {
        MaterialTheme.colorScheme.surfaceContainerHigh
    } else {
        Color.White
    }
    val textColor = if (isDarkTheme) MaterialTheme.colorScheme.onSurface else tertiaryContainerLight

    Column(
        modifier = modifier
            .clickable(role = Role.Button, onClick = onClick)
            .width(180.dp)
            .testTag("materiaCard")
            .drawBehind {
                drawRect(
                    color = outlineColor,
                    topLeft = Offset(4.dp.toPx(), 4.dp.toPx()),
                    size = size
                )
            }
            .border(2.dp, outlineColor)
            .background(cardBackground)
            .padding(16.dp)
    ) {
        Text(
            text = nombreMateria,
            color = textColor,
            fontFamily = BangersFont
        )
        Text(
            text = profesor,
            color = textColor,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(8.dp))

        val estrellas = generarEstrellas(calificacion)
        Text(
            text = estrellas,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.numero_resenas, numeroResenas),
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = ArvoFont
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
@Preview
fun MateriaPreview() {
    Materia(
        calificacion = 5,
        nombreMateria = "Desarrollo Móvil",
        profesor = "Angarita",
        numeroResenas = 18,
    )
}
