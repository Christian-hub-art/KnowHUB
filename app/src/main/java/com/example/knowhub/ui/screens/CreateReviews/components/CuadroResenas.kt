package com.example.knowhub.ui.screens.CreateReviews.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.FormLabel
import com.example.knowhub.ui.utils.FormTextField
import com.example.knowhub.ui.utils.generarEstrellas

@Composable
fun CuadroResenas(
    clase: String,
    tituloMateria: String,
    nombreProfesor: String,
    resena: String,
    onClaseChange: (String) -> Unit,
    onTituloMateriaChange: (String) -> Unit,
    onNombreProfesorChange: (String) -> Unit,
    onResenaChange: (String) -> Unit,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Cuadro de Reseña
    val colorSombra = tertiaryContainerLight

    Box(
        modifier = modifier
            .width(350.dp)
            .background(primaryLight)
            .border(2.dp, tertiaryContainerLight)
    ) {
        Column {

            // Header Azul
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryContainerLight)
                    .border(1.dp, tertiaryContainerLight)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "# CLASE: ",
                        color = primaryLight,
                        fontFamily = BangersFont,
                        fontSize = 20.sp
                    )

                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .background(primaryLight)
                            .border(1.dp, tertiaryContainerLight)
                            .padding(horizontal = 4.dp)
                    ) {
                        BasicTextField(
                            value = clase,
                            onValueChange = onClaseChange
                        )
                    }
                }

                Text(
                    text = "15 NOV 2025",
                    color = primaryLight,
                    fontSize = 20.sp
                )
            }

            // Contenido del Formulario
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                FormLabel(text = "TITULO DE LA MATERIA:")

                FormTextField(
                    value = tituloMateria,
                    onValueChange = onTituloMateriaChange,
                    placeholder = "ESCOGE LA MATERIA..."
                )

                Spacer(modifier = Modifier.height(12.dp))

                FormLabel(text = "NOMBRE DEL PROFESOR:")

                FormTextField(
                    value = nombreProfesor,
                    onValueChange = onNombreProfesorChange,
                    placeholder = "ESCRIBE EL NOMBRE DEL PROFESOR..."
                )

                Spacer(modifier = Modifier.height(12.dp))

                FormLabel(text = "TU RESEÑA")

                FormTextField(
                    value = resena,
                    onValueChange = onResenaChange,
                    placeholder = "ESCRIBE AQUÍ TU RESEÑA...",
                    modifier = Modifier.height(100.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                FormLabel(text = "TU CALIFICACIÓN")

                Row {
                    val estrellas= generarEstrellas(4)
                    Text(
                        text = estrellas,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    AppButton(
                        textoButon = "X CANCELAR",
                        colorTexto = tertiaryContainerLight,
                        colorBoton =primaryLight,
                        onClick = onClick,
                        modifier = Modifier
                            .height(40.dp)
                            .width(120.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    AppButton(
                        textoButon = "PUBLICAR",
                        colorTexto = primaryLight,
                        colorBoton = primaryContainerLight,
                        onClick = onClick,
                        modifier = Modifier
                            .height(40.dp)
                            .width(120.dp)
                    )
                }
            }
        }
    }
}