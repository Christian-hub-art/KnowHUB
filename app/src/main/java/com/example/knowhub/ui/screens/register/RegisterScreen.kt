package com.example.knowhub.ui.screens.register

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.register.components.SignUPMessage
import com.example.knowhub.ui.screens.register.components.TextosField
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.BangersFont
import com.example.knowhub.ui.utils.LogoApp
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier
){
    Box(modifier=modifier){
        BackgroundImage()
        BodyRegisterScreen()
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterScreenPreview(){
    RegisterScreen()
}

@Composable
fun BodyRegisterScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoApp()
        SignUPMessage(
            modifier = Modifier

        )
        Spacer(modifier = Modifier.weight(1.0f))

        TextosField(
            modifier = Modifier

        )

        AppButton("Registrarse",
            colorResource(R.color.blancoKnowHUB),
            modifier = Modifier
                .background(
                    color = colorResource(R.color.NegroKnowHUB)
                    )
                .height(40.dp)
                .width(280.dp)
        )

        Text("¿Ya tienes cuenta? Login ",
            fontFamily = BangersFont
        )

        Spacer(modifier = Modifier.weight(1.0f))

    }
}






