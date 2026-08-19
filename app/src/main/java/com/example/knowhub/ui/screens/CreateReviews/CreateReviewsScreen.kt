package com.example.knowhub.ui.screens.CreateReviews

import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.CreateReviews.components.CuadroResenas

import com.example.knowhub.ui.screens.CreateReviews.components.InformaciónUsuario
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.*

@Composable
fun CreateReviewsScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        BackgroundImage()
        var clase by remember { mutableStateOf("") }
        var tituloMateria by remember { mutableStateOf("") }
        var nombreProfesor by remember { mutableStateOf("") }
        var resena by remember { mutableStateOf("") }
        BodyCreateReviewsScreen(
            clase,
            tituloMateria,
            nombreProfesor,
            resena,
            onClaseChange = { clase = it },
            onTituloMateriaChange = { tituloMateria = it },
            onNombreProfesorChange = { nombreProfesor = it },
            onResenaChange = { resena = it }
        )
    }
}

@Composable
fun BodyCreateReviewsScreen(
    clase: String,
    tituloMateria: String,
    nombreProfesor: String,
    resena: String,
    onClaseChange: (String) -> Unit,
    onTituloMateriaChange: (String) -> Unit,
    onNombreProfesorChange: (String) -> Unit,
    onResenaChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        AppLabelBig(
            texto = "CREA UNA NUEVA RESEÑA",
            colorTexto = primaryLight,
            color = tertiaryContainerLight,
            modifier = Modifier
                .width(350.dp)
                .height(45.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Usuario Info
        InformaciónUsuario()

        Spacer(modifier = Modifier.height(15.dp))

        CuadroResenas(clase,
            tituloMateria,
            nombreProfesor,
            resena,
            onClaseChange = { onClaseChange(it) },
            onTituloMateriaChange = { onTituloMateriaChange(it) },
            onNombreProfesorChange = { onNombreProfesorChange(it) },
            onResenaChange = { onResenaChange(it) })

        Spacer(modifier = Modifier.height(40.dp))
    }
}





@Preview(showBackground = true)
@Composable
fun CreateReviewsPreview() {
    CreateReviewsScreen()
}





