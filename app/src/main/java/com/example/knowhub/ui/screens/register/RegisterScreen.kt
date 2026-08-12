package com.example.knowhub.ui.screens.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.ui.screens.register.components.SignUPMessage
import com.example.knowhub.ui.screens.register.components.TextosField
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.LogoApp
import com.example.knowhub.ui.utils.backgroundImage

@Composable
fun RegistrarScreen(
    modifier: Modifier = Modifier
){
    Box(){
        backgroundImage()
        bodyRegistrarScreen()
    }
}

@Composable
@Preview(showBackground = true)
fun RegistrarScreenPreview(){
    RegistrarScreen()
}

@Composable
fun bodyRegistrarScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoApp()
        SignUPMessage(
            modifier = Modifier
                .offset(y = (-50).dp)
        )

        TextosField(
            modifier = Modifier
                .offset(y = (-40).dp)
        )

        AppButton("Registrarse",
            modifier = Modifier
                .offset(y = (-30).dp)
        )
        Text("¿Ya tienes cuenta? Login ",
            fontFamily = FontFamily.Monospace)
    }
}






