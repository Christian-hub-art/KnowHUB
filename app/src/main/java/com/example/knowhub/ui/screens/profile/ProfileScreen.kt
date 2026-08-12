package com.example.knowhub.ui.screens.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.profile.components.BotonEliminarCuenta
import com.example.knowhub.ui.screens.profile.components.CuadroInformaciónPersonal
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.backgroundImage
import com.example.knowhub.ui.utils.barraArriba

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box() {
        backgroundImage()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            barraArriba(
                R.drawable.casa,
                ""
            )
            bodyProfileScreen()
        }
    }
}

@Composable
fun bodyProfileScreen(
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppButton(
            "Tu perfil",
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Image(
            painter = painterResource(R.drawable.perfil),
            contentDescription = stringResource(R.string.imagen_perfil),
            modifier = modifier
                .background(colorResource(R.color.blancoKnowHUB))
                .border(2.dp, colorResource(R.color.NegroKnowHUB))
        )
        Spacer(modifier = Modifier.padding(10.dp))
        AppButton("\uD83D\uDCF8 Subir Foto")
        Spacer(modifier = Modifier.padding(10.dp))

        CuadroInformaciónPersonal()

        Spacer(modifier = Modifier.height(10.dp))

        BotonEliminarCuenta()
    }
}



@Composable
@Preview()
fun ProfileScreenPreview(){
    ProfileScreen()
}