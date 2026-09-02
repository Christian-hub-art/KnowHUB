package com.example.knowhub.ui.screens.options


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
//Pantalla principal de opciones de usuario y configuración del perfil.
@Composable
fun OptionsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        BackgroundImage()
        BodyOptionsScreen()
    }
}
//Contenido del cuerpo de la pantalla de opciones.
@Composable
fun BodyOptionsScreen(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(35.dp)) // Ajustado para que no choque con la TopBar si es necesario

            AppLabel(
                stringResource(R.string.perfil),
                primaryLight,
                tertiaryContainerLight,
                modifier = Modifier
                    .height(40.dp)
                    .width(320.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(R.drawable.perfil),
                contentDescription = stringResource(R.string.imagen_perfil),
                modifier = Modifier
                    .background(primaryLight)
                    .border(1.dp, tertiaryContainerLight)
                    .size(150.dp) // Añadido tamaño explícito para que se vea mejor
            )
            Spacer(modifier = Modifier.height(20.dp))

            AppButton(
                stringResource(R.string.subir_foto),
                tertiaryContainerLight,
                primaryLight,
                modifier = Modifier
                    .height(30.dp)
                    .width(180.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            /*  CuadroInformaciónPersonal() */

            Spacer(modifier = Modifier.height(20.dp))
            AppButton(
                stringResource(R.string.eliminar_cuenta),
                tertiaryContainerLight,
                secondaryContainerLight,
                modifier = Modifier
                    .height(40.dp)
                    .width(180.dp)
            )
        }
    }
}

@Preview
@Composable
fun OptionsScreenPreview() {
    KnowHUBTheme {
        OptionsScreen()
    }
}
