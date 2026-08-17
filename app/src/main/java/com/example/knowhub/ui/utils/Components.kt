package com.example.knowhub.ui.utils


import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.knowhub.ui.screens.register.RegisterScreen
import com.example.knowhub.ui.screens.reviews.ReviewScreen


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
    textoButon: String,
    colorTexto: Color,
    colorBoton: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = colorBoton
        ),
        shape = RectangleShape,
        border = BorderStroke(
            2.dp,
            colorResource(R.color.NegroKnowHUB)
        ),
        modifier = modifier
    ) {
        Text(
            text = textoButon,
            color = colorTexto,
            textAlign = TextAlign.Center,
            fontFamily = BangersFont,
            fontWeight = FontWeight.Bold
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
            .height(80.dp)
            .background(
                colorResource(R.color.blancoKnowHUB)
            )
            .border(
                width = 2.dp,
                color = colorResource(R.color.NegroKnowHUB),
                shape = RectangleShape
            )
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
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun Message(
    texto: String,
    modifier: Modifier = Modifier
){
    Text(
        texto,
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = ArvoFont,
        modifier = modifier
    )

}

@Composable
fun CuadroTexto(
    textoCuadro: String,
    fondoCuadro: Color,
    alto: Dp,
    ancho: Dp,
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier
            .height(alto)
            .width(ancho)
            .background(fondoCuadro)
            .border(
                2.dp,
                colorResource(R.color.NegroKnowHUB)
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            textoCuadro,
            textAlign = TextAlign.Center,
            fontFamily = BangersFont)
    }
}

