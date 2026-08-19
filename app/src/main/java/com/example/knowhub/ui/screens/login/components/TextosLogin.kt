package com.example.knowhub.ui.screens.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.knowhub.R
import com.example.knowhub.ui.theme.*


@Composable
fun TextosLogin(
    correoOUsuario: String,
    contrasena: String,
    onCorreoOUsuarioChange: (String) ->Unit,
    onContrasenaChange: (String) ->Unit,
    modifier : Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = correoOUsuario,
            onValueChange = onCorreoOUsuarioChange,
            label = { Text(stringResource(R.string.correo_electr_nico_o_usuario)) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = primaryLight,
                focusedContainerColor = primaryLight,
                focusedBorderColor = tertiaryContainerLight,
                unfocusedBorderColor = tertiaryContainerLight
            )
        )
        OutlinedTextField(
            value = contrasena,
            onValueChange = onContrasenaChange,
            label = { Text(stringResource(R.string.contrase_a)) },
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = primaryLight,
                focusedContainerColor = primaryLight,
                focusedBorderColor = tertiaryContainerLight,
                unfocusedBorderColor = tertiaryContainerLight
            )
        )
    }
}
