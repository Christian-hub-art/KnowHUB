package com.example.knowhub.ui.utils


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R


@Composable
fun backgroundImage(){
    Image(
        painter = painterResource(R.drawable.fondoknowhub),
        contentDescription = "Fondo KnowHUB",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LogoApp(
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(R.drawable.logoknowhub),
        contentDescription = "Logo KnowHUB",
        modifier = modifier

    )
}

@Composable
fun AppButton(textoBoton: String,
              modifier: Modifier= Modifier
)
{
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.NegroKnowHUB)
        ), modifier = modifier


    ){
        Text(
            textoBoton,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun barraArriba(
    idImagen: Int,
    texto: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {


        Image(
            painter = painterResource(R.drawable.logoknowhub),
            contentDescription = "Logo KnowHUB",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        )


        Text(
            text = texto,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        ) {
            Icon(
                painter = painterResource(idImagen),
                contentDescription = "Icono",
                modifier = Modifier.size(70.dp)            )
        }
    }
}

