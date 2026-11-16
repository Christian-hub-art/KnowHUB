package com.example.knowhub.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.knowhub.R
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            viewModel.onNavigatedBack()
            onBack()
        }
    }

    androidx.compose.foundation.layout.Box {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(onClick = onBack) {
                Text(stringResource(R.string.volver))
            }
            AppLabel(
                stringResource(R.string.modificar_datos),
                colorResource(R.color.blancoKnowHUB),
                colorResource(R.color.NegroKnowHUB),
                modifier = Modifier.fillMaxWidth()
            )

            EditProfileField(
                value = state.nombre,
                label = stringResource(R.string.nombre_de_usuario),
                onValueChange = viewModel::updateNombre
            )
            EditProfileField(
                value = state.correo,
                label = stringResource(R.string.correo_electr_nico),
                onValueChange = viewModel::updateCorreo,
                keyboardType = KeyboardType.Email
            )

            Text(
                text = stringResource(R.string.cambiar_contrasena),
                fontFamily = BangersFont,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            EditProfileField(
                value = state.contrasenaActual,
                label = stringResource(R.string.contrasena_actual),
                onValueChange = viewModel::updateContrasenaActual,
                isPassword = true
            )
            EditProfileField(
                value = state.nuevaContrasena,
                label = stringResource(R.string.nueva_contrasena),
                onValueChange = viewModel::updateNuevaContrasena,
                isPassword = true
            )
            EditProfileField(
                value = state.confirmarContrasena,
                label = stringResource(R.string.confirmar_nueva_contrasena),
                onValueChange = viewModel::updateConfirmarContrasena,
                isPassword = true
            )

            state.errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
            state.successMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.primary)
            }

            if (state.isSaving) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            AppButton(
                textoButon = stringResource(R.string.guardar_cambios),
                colorTexto = colorResource(R.color.blancoKnowHUB),
                colorBoton = colorResource(R.color.AzulKnowHUB),
                onClick = viewModel::saveChanges,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                enabled = !state.isSaving
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun EditProfileField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedLabelColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onSurface
            } else {
                androidx.compose.ui.graphics.Color.Black
            },
            cursorColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onSurface
            } else {
                androidx.compose.ui.graphics.Color.Black
            },
            focusedBorderColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onSurface
            } else {
                androidx.compose.ui.graphics.Color.Black
            },
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
