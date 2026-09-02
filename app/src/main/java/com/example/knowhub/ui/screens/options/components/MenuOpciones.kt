package com.example.knowhub.ui.screens.options.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.*

//Menú lateral de navegación con las opciones principales de la aplicación.
@Composable
fun MenuOpciones(
    inicioButtonPressed: () -> Unit,
    profileButtonPressed: () -> Unit,
    previewButtonPressed: () -> Unit,
    notificationsButtonPressed: () -> Unit,
    createReviewsButtonPressed: () -> Unit,
    buscarButtonPressed: () -> Unit,
    cerrarSesionButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxHeight()
        .width(250.dp)
        .background(primaryLight)
        .border(
            4.dp,
            secondaryLight
        )
        .padding(15.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.4F))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { inicioButtonPressed() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.casa),
                contentDescription = stringResource(R.string.inicio),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                stringResource(R.string.explorar),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ArvoFont,
            )
        }
        Spacer(modifier = Modifier.weight(0.1F))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { profileButtonPressed() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.inciotarea),
                contentDescription = stringResource(R.string.perfil),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                stringResource(R.string.perfil),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ArvoFont,
            )
        }
        Spacer(modifier = Modifier.weight(0.1F))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { previewButtonPressed() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.libro),
                contentDescription = stringResource(R.string.rese_a),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                stringResource(R.string.tus_rese_as),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ArvoFont,
            )
        }
        Spacer(modifier = Modifier.weight(0.1F))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { notificationsButtonPressed() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.notificacion),
                contentDescription = stringResource(R.string.notificaci_n),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                stringResource(R.string.notificaciones),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ArvoFont,
            )
        }
        Spacer(modifier = Modifier.weight(0.1F))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { createReviewsButtonPressed() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.mas),
                contentDescription = stringResource(R.string.crear_resena),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                stringResource(R.string.crear_resena),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ArvoFont,
            )
        }
        Spacer(modifier = Modifier.weight(0.1F))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { buscarButtonPressed() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.buscar),
                contentDescription =stringResource(R.string.imagen_buscar),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                stringResource(R.string.buscar),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ArvoFont,
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { cerrarSesionButtonPressed() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                stringResource(R.string.cerrar_sesion),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ArvoFont,
            )
        }
    }

}

@Composable
@Preview
fun MenuOpcionesPreview() {
    MenuOpciones(
        inicioButtonPressed = {},
        profileButtonPressed = {},
        previewButtonPressed = {},
        notificationsButtonPressed = {},
        createReviewsButtonPressed = {},
        buscarButtonPressed = {},
        cerrarSesionButtonPressed = {}
    )
}
