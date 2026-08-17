package com.example.knowhub.ui.screens.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.knowhub.R


@Composable
fun TextosLogin(
    modifier : Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Correo electrónico o usuario") },
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
            label = { Text("Contraseña") },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedContainerColor = colorResource(R.color.blancoKnowHUB),
                focusedBorderColor = colorResource(R.color.NegroKnowHUB),
                unfocusedBorderColor = colorResource(R.color.NegroKnowHUB)
            )
        )
    }
}
