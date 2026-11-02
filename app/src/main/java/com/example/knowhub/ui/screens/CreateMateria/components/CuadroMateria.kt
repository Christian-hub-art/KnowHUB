package com.example.knowhub.ui.screens.CreateMateria.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.FormLabel
import com.example.knowhub.ui.utils.FormTextField
//Formulario en tarjeta para crear o editar una materia.
@Composable
fun CuadroMateria(
    nombreMateria: String,
    nombreProfesor: String,
    codigo: String,
    dificultad: String,
    hashtags: String,
    editando: Boolean,
    onNombreMateriaChange: (String) -> Unit,
    onNombreProfesorChange: (String) -> Unit,
    onCodigoChange: (String) -> Unit,
    onDificultadChange: (String) -> Unit,
    onHashtagsChange: (String) -> Unit,
    onClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(350.dp)
            .background(primaryLight)
            .border(2.dp, tertiaryContainerLight)
    ) {
        Column {

            // Header Azul con el código de la materia
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryContainerLight)
                    .border(1.dp, tertiaryContainerLight)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
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
                    // El código es el id de la materia, por eso no se cambia al editar
                    if (editando) {
                        Text(codigo)
                    } else {
                        BasicTextField(
                            value = codigo,
                            onValueChange = onCodigoChange
                        )
                    }
                }
            }

            // Contenido del Formulario
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                FormLabel(text = stringResource(R.string.titulo_materia_label))

                FormTextField(
                    value = nombreMateria,
                    onValueChange = onNombreMateriaChange,
                    placeholder = stringResource(R.string.escribir_titulo_de_la_materia)
                )

                Spacer(modifier = Modifier.height(12.dp))

                FormLabel(text = stringResource(R.string.nombre_profesor_label))

                FormTextField(
                    value = nombreProfesor,
                    onValueChange = onNombreProfesorChange,
                    placeholder = stringResource(R.string.escribe_nombre_profesor_placeholder)
                )

                Spacer(modifier = Modifier.height(12.dp))

                FormLabel(text = stringResource(R.string.dificultad_materia_label))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Baja", "Media", "Alta").forEach { opcion ->
                        AppButton(
                            textoButon = opcion,
                            colorTexto = if (dificultad == opcion) primaryLight else tertiaryContainerLight,
                            colorBoton = if (dificultad == opcion) primaryContainerLight else secondaryContainerLight,
                            onClick = { onDificultadChange(opcion) },
                            modifier = Modifier.height(40.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                FormLabel(text = stringResource(R.string.hashtags_label))

                FormTextField(
                    value = hashtags,
                    onValueChange = onHashtagsChange,
                    placeholder = stringResource(R.string.hashtags_placeholder)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    AppButton(
                        textoButon = stringResource(R.string.cancelar_con_x),
                        colorTexto = tertiaryContainerLight,
                        colorBoton = primaryLight,
                        onClick = onCancelarClick,
                        modifier = Modifier
                            .height(40.dp)
                            .width(120.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    AppButton(
                        textoButon = stringResource(R.string.guardar),
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
