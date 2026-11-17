package com.example.knowhub.ui.screens.reviewMap.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.BangersFont

//Punto azul que marca en el mapa la ubicación obtenida con "Centrar en mi ubicación".
@Composable
fun UserLocationMarker() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.mapa_estas_aqui),
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(REVIEW_HEADER_BLUE)
                .padding(horizontal = 8.dp, vertical = 2.dp),
            color = Color.White,
            fontFamily = BangersFont,
            fontSize = 12.sp
        )
        Box(
            modifier = Modifier
                .padding(top = 3.dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(REVIEW_HEADER_BLUE)
                .border(3.dp, Color.White, CircleShape)
        )
    }
}
