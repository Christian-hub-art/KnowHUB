package com.example.knowhub.ui.screens.register.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.knowhub.ui.utils.ArvoFont

@Composable
fun SignUPMessage(
    modifier: Modifier = Modifier
){
    Text(
        "Sign Up",
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = ArvoFont,
        modifier = modifier
    )

}