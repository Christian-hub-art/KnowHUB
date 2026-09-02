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
import androidx.compose.ui.res.stringResource
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
//Formulario en tarjeta para la creación de una nueva reseña de asignatura y profesor.
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
                        text = stringResource(R.string.clase_header),
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
                    text = stringResource(R.string.fecha_default),
                    color = primaryLight,
                    fontSize = 20.sp
                )
            }

            // Contenido del Formulario
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                FormLabel(text = stringResource(R.string.titulo_materia_label))

                FormTextField(
                    value = tituloMateria,
                    onValueChange = onTituloMateriaChange,
                    placeholder = stringResource(R.string.escoge_materia_placeholder)
                )

                Spacer(modifier = Modifier.height(12.dp))

                FormLabel(text = stringResource(R.string.nombre_profesor_label))

                FormTextField(
                    value = nombreProfesor,
                    onValueChange = onNombreProfesorChange,
                    placeholder = stringResource(R.string.escribe_nombre_profesor_placeholder)
                )

                Spacer(modifier = Modifier.height(12.dp))

                FormLabel(text = stringResource(R.string.tu_resena_label))

                FormTextField(
                    value = resena,
                    onValueChange = onResenaChange,
                    placeholder = stringResource(R.string.escribe_aqui_resena_placeholder),
                    modifier = Modifier.height(100.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                FormLabel(text = stringResource(R.string.tu_calificacion_label))

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
                        textoButon = stringResource(R.string.cancelar_con_x),
                        colorTexto = tertiaryContainerLight,
                        colorBoton =primaryLight,
                        onClick = {},
                        modifier = Modifier
                            .height(40.dp)
                            .width(120.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    AppButton(
                        textoButon = stringResource(R.string.publicar),
                        colorTexto = primaryLight,
                        colorBoton = primaryContainerLight,
                        onClick = {},
                        modifier = Modifier
                            .height(40.dp)
                            .width(120.dp)
                    )
                }
            }
        }
    }
}