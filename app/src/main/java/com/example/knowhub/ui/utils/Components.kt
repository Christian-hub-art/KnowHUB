package com.example.knowhub.ui.utils


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.knowhub.ui.screens.register.RegisterScreen
import com.example.knowhub.ui.screens.reviews.ReviewScreen
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight


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
    colorTexto: Color? =null,
    colorBoton: Color? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorBoton ?: MaterialTheme.colorScheme.primary
        ),
        shape = RectangleShape,
        border = BorderStroke(
            2.dp,
            tertiaryContainerLight
        ),
        modifier = modifier
    ) {
        Text(
            text = textoButon,
            color = colorTexto ?: Color.Unspecified,
            textAlign = TextAlign.Center,
            fontFamily = BangersFont,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AppLabel(
    texto: String,
    colorTexto: Color? = null,
    color: Color? = null,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color ?: MaterialTheme.colorScheme.primary)
            .border(
                2.dp,
                tertiaryContainerLight
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = texto,
            color = colorTexto ?: Color.Unspecified,
            textAlign = TextAlign.Center,
            fontFamily = BangersFont,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AppLabelBig(
    texto: String,
    colorTexto: Color? = null,
    color: Color? = null,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color ?: MaterialTheme.colorScheme.primary)
            .border(
                2.dp,
                tertiaryContainerLight
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = texto,
            color = colorTexto ?: Color.Unspecified,
            textAlign = TextAlign.Center,
            fontFamily = BangersFont,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
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
                primaryLight
            )
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = tertiaryContainerLight,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
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
                tertiaryContainerLight
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            textoCuadro,
            textAlign = TextAlign.Center,
            fontFamily = BangersFont)
    }
}

@Composable
fun AppButtonBig(
    textoButon: String,
    colorTexto: Color,
    colorBoton: Color,
    onClick: () -> Unit ={},
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
            tertiaryContainerLight
        ),
        modifier = modifier
    ) {
        Text(
            text = textoButon,
            color = colorTexto,
            textAlign = TextAlign.Center,
            fontFamily = BangersFont,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
    }
}

@Composable
fun FormLabel(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        label = { Text("$placeholder") },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = primaryLight,
            focusedContainerColor = primaryLight,
            focusedBorderColor = tertiaryContainerLight,
            unfocusedBorderColor = tertiaryContainerLight
        )
    )
}


@Composable
fun CajaBusqueda(
    fecha: String,
    idMateria: String,
    nombreMateria: String,
    nombreProfesor: String,
    estrellas: Int,
    numeroResenas: Int,
    tags: List<String>,
    dificultad: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(primaryLight)
            .border(2.dp, tertiaryContainerLight)
    ) {
        Column {
            // Header Azul
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryContainerLight)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CLASE: $idMateria",
                    color = primaryLight,
                    fontFamily = BangersFont,
                    fontSize = 24.sp
                )
                Text(
                    text = fecha,
                    color = primaryLight,
                    fontFamily = BangersFont,
                    fontSize = 20.sp
                )
            }

            // Cuerpo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = nombreMateria,
                        fontFamily = BangersFont,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic,
                        color = tertiaryContainerLight
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "👩‍🏫", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Prof. $nombreProfesor",
                            fontSize = 16.sp,
                            color = tertiaryContainerLight
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = generarEstrellas(estrellas),
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Basado en $numeroResenas reseñas",
                        fontSize = 14.sp,
                        color = tertiaryContainerLight
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        tags.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .background(primaryContainerLight)
                                    .border(1.dp, tertiaryContainerLight)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = tag.uppercase(),
                                    color = primaryLight,
                                    fontFamily = BangersFont,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                // Dificultad (Bottom Right)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .border(2.dp, tertiaryContainerLight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(secondaryContainerLight) // Amarillo
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = dificultad.uppercase(),
                            fontFamily = BangersFont,
                            fontSize = 12.sp,
                            color = tertiaryContainerLight,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(primaryLight)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "DIFICULTAD",
                            fontFamily = BangersFont,
                            fontSize = 10.sp,
                            color = tertiaryContainerLight,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryContainerLight)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VER RESEÑAS COMPLETAS",
                    color = primaryLight,
                    fontFamily = BangersFont,
                    fontSize = 20.sp
                )
                /*Icon(
                    painter = painterResource(id = R.drawable.), // Asumiendo que existe
                    contentDescription = null,
                    tint = primaryLight,
                    modifier = Modifier.size(24.dp)
                )*/
            }
        }
    }
}