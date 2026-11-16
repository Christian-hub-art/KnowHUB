package com.example.knowhub.ui.screens.login.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.knowhub.R
import com.example.knowhub.ui.theme.*
//Componente que agrupa los campos de entrada de texto para el inicio de sesión

@Composable
fun TextosLogin(
    correoOUsuario: String,
    contrasena: String,
    onCorreoOUsuarioChange: (String) ->Unit,
    onContrasenaChange: (String) ->Unit,
    modifier : Modifier = Modifier
) {
    val fieldFocusColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Column(modifier = modifier) {
        OutlinedTextField(
            value = correoOUsuario,
            onValueChange = onCorreoOUsuarioChange,
            modifier = Modifier.testTag("loginEmailOrUserField"),
            label = { Text(stringResource(R.string.correo_electr_nico_o_usuario)) },
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
        OutlinedTextField(
            value = contrasena,
            onValueChange = onContrasenaChange,
            modifier = Modifier.testTag("loginPasswordField"),
            label = { Text(stringResource(R.string.contrase_a)) },
            visualTransformation = PasswordVisualTransformation(),
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
}
