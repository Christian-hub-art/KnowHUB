package com.example.knowhub.ui.screens.options.components

import androidx.compose.foundation.border
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.*

@Composable
fun BotonEliminarCuenta(
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = secondaryContainerLight
        ),
        modifier = modifier.border(
            2.dp,
            tertiaryContainerLight
        )
    ) {
        Text(
            "ELIMINAR CUENTA",
            fontFamily = FontFamily.Monospace,
            color = Color.Black
        )
    }
}