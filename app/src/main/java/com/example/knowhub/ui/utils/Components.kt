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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.testTag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight


@Composable
fun BackgroundImage(){
    if (isSystemInDarkTheme()) {
        val context = LocalContext.current
        val backgroundBitmap = remember(context) {
            context.assets.open("fondoOscuro.png").use { stream ->
                requireNotNull(BitmapFactory.decodeStream(stream)) {
                    "No se pudo cargar app/src/main/assets/fondoOscuro.png"
                }.asImageBitmap()
            }
        }
        Image(
            bitmap = backgroundBitmap,
            contentDescription = stringResource(R.string.fondo_knowhub),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = painterResource(R.drawable.fondoknowhub),
            contentDescription = stringResource(R.string.fondo_knowhub),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun LogoApp(
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(R.drawable.logoknowhub),
        contentDescription = stringResource(R.string.logo_knowhub),
        modifier = modifier.height(210.dp).width(250.dp)

    )
}

@Composable
fun AppButton(
    textoButon: String,
    colorTexto: Color? =null,
    colorBoton: Color? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val darkTheme = isSystemInDarkTheme()
    val buttonColor = if (!darkTheme) {
        colorBoton ?: MaterialTheme.colorScheme.primary
    } else {
        when (colorBoton) {
            primaryContainerLight -> primaryContainerLight
            secondaryContainerLight -> secondaryContainerLight
            else -> MaterialTheme.colorScheme.surfaceContainerHigh
        }
    }
    val textColor = if (!darkTheme) {
        colorTexto ?: Color.Unspecified
    } else if (colorTexto == primaryContainerLight || colorTexto == secondaryContainerLight) {
        colorTexto
    } else {
        when (buttonColor) {
            primaryContainerLight -> primaryLight
            secondaryContainerLight -> tertiaryContainerLight
            else -> MaterialTheme.colorScheme.onSurface
        }
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        shape = RectangleShape,
        border = BorderStroke(
            2.dp,
            if (darkTheme) MaterialTheme.colorScheme.outline else tertiaryContainerLight
        ),
        modifier = modifier
    ) {
        Text(
            text = textoButon,
            color = textColor,
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
    val darkTheme = isSystemInDarkTheme()
    val labelColor = if (!darkTheme) {
        color ?: MaterialTheme.colorScheme.primary
    } else {
        when (color) {
            primaryContainerLight -> primaryContainerLight
            secondaryContainerLight -> secondaryContainerLight
            else -> MaterialTheme.colorScheme.surfaceContainerHigh
        }
    }
    val labelTextColor = if (!darkTheme) {
        colorTexto ?: Color.Unspecified
    } else if (colorTexto == primaryContainerLight || colorTexto == secondaryContainerLight) {
        colorTexto
    } else {
        when (labelColor) {
            primaryContainerLight -> primaryLight
            secondaryContainerLight -> tertiaryContainerLight
            else -> MaterialTheme.colorScheme.onSurface
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(labelColor)
            .border(
                2.dp,
                if (darkTheme) MaterialTheme.colorScheme.outline else tertiaryContainerLight
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = texto,
            color = labelTextColor,
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
    val darkTheme = isSystemInDarkTheme()
    val labelColor = if (!darkTheme) {
        color ?: MaterialTheme.colorScheme.primary
    } else {
        when (color) {
            primaryContainerLight -> primaryContainerLight
            secondaryContainerLight -> secondaryContainerLight
            else -> MaterialTheme.colorScheme.surfaceContainerHigh
        }
    }
    val labelTextColor = if (!darkTheme) {
        colorTexto ?: Color.Unspecified
    } else if (colorTexto == primaryContainerLight || colorTexto == secondaryContainerLight) {
        colorTexto
    } else {
        when (labelColor) {
            primaryContainerLight -> primaryLight
            secondaryContainerLight -> tertiaryContainerLight
            else -> MaterialTheme.colorScheme.onSurface
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(labelColor)
            .border(
                2.dp,
                if (darkTheme) MaterialTheme.colorScheme.outline else tertiaryContainerLight
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = texto,
            color = labelTextColor,
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
                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else primaryLight
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
            color = MaterialTheme.colorScheme.onSurface,
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
            .background(
                if (isSystemInDarkTheme() &&
                    fondoCuadro != primaryContainerLight &&
                    fondoCuadro != secondaryContainerLight
                ) MaterialTheme.colorScheme.surfaceContainerHigh else fondoCuadro
            )
            .border(
                2.dp,
                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.outline else tertiaryContainerLight
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
    val darkTheme = isSystemInDarkTheme()
    val buttonColor = if (!darkTheme || colorBoton == primaryContainerLight || colorBoton == secondaryContainerLight) {
        colorBoton
    } else {
        MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val textColor = if (!darkTheme) {
        colorTexto
    } else if (colorTexto == primaryContainerLight || colorTexto == secondaryContainerLight) {
        colorTexto
    } else {
        when (buttonColor) {
            primaryContainerLight -> primaryLight
            secondaryContainerLight -> tertiaryContainerLight
            else -> MaterialTheme.colorScheme.onSurface
        }
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        shape = RectangleShape,
        border = BorderStroke(
            2.dp,
            if (darkTheme) MaterialTheme.colorScheme.outline else tertiaryContainerLight
        ),
        modifier = modifier
    ) {
        Text(
            text = textoButon,
            color = textColor,
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
            .fillMaxWidth(),
        label = { Text("$placeholder") },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
}


@Composable
fun CajaBusqueda(
    generalReview : GeneralReview,
    generalReviewPressed: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else primaryLight
            )
            .border(
                2.dp,
                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.outline else tertiaryContainerLight
            )
            .clickable { generalReviewPressed (generalReview.id) }
            .testTag("cajaBusquedaCard"),
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
                    text = stringResource(R.string.codigo_clase_label, generalReview.codigoAsignatura),
                    color = primaryLight,
                    fontFamily = BangersFont,
                    fontSize = 24.sp
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
                        text = generalReview.nombreMateria,
                        fontFamily = BangersFont,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic,
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            tertiaryContainerLight
                        }
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "👩‍🏫", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.prof_label, generalReview.nombreProfesor),
                            fontSize = 16.sp,
                            color = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                tertiaryContainerLight
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = generarEstrellas(generalReview.calificacionMedia),
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.basado_en_resenas, generalReview.cantidadReviews),
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            tertiaryContainerLight
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        generalReview.Hashtags.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .background(primaryContainerLight)
                                    .border(
                                        1.dp,
                                        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.outline else tertiaryContainerLight
                                    )
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
                        .border(
                            2.dp,
                            if (isSystemInDarkTheme()) MaterialTheme.colorScheme.outline else tertiaryContainerLight
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(secondaryContainerLight) // Amarillo
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = generalReview.dificultadMedia.uppercase(),
                            fontFamily = BangersFont,
                            fontSize = 12.sp,
                            color = tertiaryContainerLight,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                if (isSystemInDarkTheme()) {
                                    MaterialTheme.colorScheme.surface
                                } else {
                                    primaryLight
                                }
                            )
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.dificultad),
                            fontFamily = BangersFont,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface,
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
                    text = stringResource(R.string.ver_resenas_completas),
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

@Composable
fun ProfileAsyncImage(
    profileImage: String?,
    size: Int,
    modifier: Modifier = Modifier
){
    if (profileImage.isNullOrBlank()) {
        Image(
            painter = painterResource(R.drawable.perfil),
            contentDescription = "User Image",
            contentScale = ContentScale.Crop,
            colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(Color.White) else null,
            modifier = modifier.size(size.dp).clip(CircleShape)
        )
    } else {
        AsyncImage(
            contentDescription = "User Image",
            model = ImageRequest.Builder(LocalContext.current)
                .data(profileImage)
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.perfil),
            placeholder = painterResource(R.drawable.loading_image),
            contentScale = ContentScale.Crop,
            modifier = modifier.size(size.dp).clip(CircleShape)
        )
    }
}