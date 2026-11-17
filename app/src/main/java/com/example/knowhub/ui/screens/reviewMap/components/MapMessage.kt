package com.example.knowhub.ui.screens.reviewMap.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.ui.theme.ArvoFont

//Mensaje informativo o de error sobre el mapa.
@Composable
fun MapMessage(
    text: String,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = panelColor(isDarkMode),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 5.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(14.dp),
            color = if (isError) Color(0xFFBA1A1A) else panelTextColor(isDarkMode),
            fontFamily = ArvoFont,
            fontSize = 12.sp
        )
    }
}
