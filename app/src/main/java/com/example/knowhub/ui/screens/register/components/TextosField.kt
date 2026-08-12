package com.example.knowhub.ui.screens.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.knowhub.R

@Composable
fun TextosField(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.nombre)) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedBorderColor = colorResource(R.color.NegroKnowHUB),
                unfocusedBorderColor = colorResource(R.color.NegroKnowHUB)
            )
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.correo_electr_nico)) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedBorderColor = colorResource(R.color.NegroKnowHUB),
                unfocusedBorderColor = colorResource(R.color.NegroKnowHUB)
            )
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.nombre_de_usuario)) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedBorderColor = colorResource(R.color.NegroKnowHUB),
                unfocusedBorderColor = colorResource(R.color.NegroKnowHUB)

            )
        )
        OutlinedTextField(
            value = "KnowHUB",
            onValueChange = {},
            label = { Text(stringResource(R.string.contrase_a)) },
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedBorderColor = colorResource(R.color.NegroKnowHUB),
                unfocusedBorderColor = colorResource(R.color.NegroKnowHUB)
            ),

            )
        OutlinedTextField(
            value = "KnowHUB",
            onValueChange = {},
            label = { Text(stringResource(R.string.confirmaci_n_de_contrase_a)) },
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedBorderColor = colorResource(R.color.NegroKnowHUB),
                unfocusedBorderColor = colorResource(R.color.NegroKnowHUB)
            )
        )
    }
}

