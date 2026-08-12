package com.example.knowhub.ui.utils


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextAlign


val BangersFont = FontFamily(
    Font(R.font.bangers_regular)
)
val ArvoFont = FontFamily(
    Font(R.font.arvo_regular)
)
@Composable
fun BackgroundImage(){
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
        modifier = modifier.height(250.dp).width(250.dp)

    )
}

@Composable
fun AppButton(
    textoBoton: String,
    colorTexto: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = colorResource(R.color.NegroKnowHUB)
            )
            .clickable() { }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )

    ) {
        Text(
            text = textoBoton,
            color = colorTexto,
            textAlign = TextAlign.Center,
            fontFamily = BangersFont,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BarraArriba(
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
            fontFamily = ArvoFont,
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

