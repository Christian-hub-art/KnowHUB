package com.example.knowhub.ui.screens.login


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.login.components.LoginMessage
import com.example.knowhub.ui.screens.login.components.TextosLogin
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.BangersFont
import com.example.knowhub.ui.utils.LogoApp


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyLoginScreen()
    }
}
@Composable
@Preview(showBackground = true)
fun LoginScreenPreview(){
    LoginScreen()
}

@Composable
fun BodyLoginScreen(
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoApp()
        LoginMessage()
        Spacer(modifier = Modifier.weight(1.0F))
        TextosLogin()
        Spacer(modifier = Modifier.weight(0.5F))
        Text("¿Olvidaste tu contraseña?",
            fontFamily = BangersFont)
        Spacer(modifier = Modifier.weight(5.0F))
        AppButton("Iniciar Sesión",
            colorResource(R.color.blancoKnowHUB),
            colorResource(R.color.NegroKnowHUB),
            modifier = modifier
                .height(30.dp)
                .width(280.dp))
        Spacer(modifier = Modifier.weight(0.5F))
        Text("¿No tienes cuenta?  Registrate",
            fontFamily = BangersFont
        )
        Spacer(modifier = Modifier.weight(10.0F))
        Text("Continuar sin registro",
            fontFamily = BangersFont)
        Spacer(modifier = Modifier.weight(1.0F))

    }

}