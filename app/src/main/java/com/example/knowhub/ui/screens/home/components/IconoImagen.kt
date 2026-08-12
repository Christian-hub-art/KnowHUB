package com.example.knowhub.ui.screens.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R

@Composable
fun IconoImagen(
    idIcono: Int,
    description: String,
    motivo: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            "${motivo}",
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace
        )

        IconButton(
            onClick = { },
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .border(
                    3.dp,
                    colorResource(R.color.NegroKnowHUB)
                )
                .padding(15.dp)
        ) {
            Icon(
                painter = painterResource(idIcono),
                contentDescription = description,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}