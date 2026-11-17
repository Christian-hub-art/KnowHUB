package com.example.knowhub.ui.screens.recuperarContrasena.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.knowhub.R
//Campo para escribir el correo al recuperar la contraseña, con el mismo estilo de Login.
@Composable
fun TextoCorreoRecuperar(
    correo: String,
    onCorreoChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val fieldFocusColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    OutlinedTextField(
        value = correo,
        onValueChange = onCorreoChange,
        modifier = modifier.testTag("recuperarCorreoField"),
        label = { Text(stringResource(R.string.correo_electr_nico)) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedLabelColor = fieldFocusColor,
            cursorColor = fieldFocusColor,
            focusedBorderColor = fieldFocusColor,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
}
