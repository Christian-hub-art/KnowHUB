package com.example.knowhub.ui.screens.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.knowhub.R
import com.example.knowhub.ui.theme.*

@Composable
fun TextosField(
    nombre: String,
    correoElectronico: String,
    nombreUsuario: String,
    contrasena: String,
    confirmarContrasena: String,
    onNombreChange: (String) ->Unit,
    onCorreoElectronicoChange: (String) ->Unit,
    onNombreUsuarioChange: (String) ->Unit,
    onContrasenaChange: (String) ->Unit,
    onConfirmarContrasenaChange: (String) ->Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text(stringResource(R.string.nombre)) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = primaryLight,
                focusedContainerColor = primaryLight,
                focusedBorderColor = tertiaryContainerLight,
                unfocusedBorderColor = tertiaryContainerLight
            )
        )
        OutlinedTextField(
            value = correoElectronico,
            onValueChange = onCorreoElectronicoChange,
            label = { Text(stringResource(R.string.correo_electr_nico)) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = primaryLight,
                focusedContainerColor = primaryLight,
                focusedBorderColor = tertiaryContainerLight,
                unfocusedBorderColor = tertiaryContainerLight
            )
        )
        OutlinedTextField(
            value = nombreUsuario,
            onValueChange = onNombreUsuarioChange,
            label = { Text(stringResource(R.string.nombre_de_usuario)) },
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
            ),

            )
        OutlinedTextField(
            value = confirmarContrasena,
            onValueChange = onConfirmarContrasenaChange,
            label = { Text(stringResource(R.string.confirmaci_n_de_contrase_a)) },
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

