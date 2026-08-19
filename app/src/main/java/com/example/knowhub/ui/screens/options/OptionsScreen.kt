package com.example.knowhub.ui.screens.options


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.options.components.MenuOpciones
import com.example.knowhub.ui.screens.profile.components.CuadroInformaciónPersonal
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.BarraArriba

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

@Composable
fun BodyOptionsScreen(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BarraArriba(
                R.drawable.iconomenu,
                ""
            )

            Spacer(modifier = Modifier.weight(17.0F))

            AppLabel(
                "Tu perfil",
                primaryLight,
                tertiaryContainerLight,
                modifier = Modifier
                    .height(40.dp)
                    .width(320.dp)
            )

            Spacer(modifier = Modifier.weight(10.0F))

            Image(
                painter = painterResource(R.drawable.perfil),
                contentDescription = stringResource(R.string.imagen_perfil),
                modifier = Modifier
                    .background(
                        primaryLight
                    )
                    .border(
                        1.dp,
                        tertiaryContainerLight
                    )
            )
            Spacer(modifier = Modifier.weight(10.0F))

            AppButton(
                "📸 Subir Foto",
                tertiaryContainerLight,
                primaryLight,
                modifier = Modifier
                    .height(30.dp)
                    .width(180.dp)

            )

            Spacer(modifier = Modifier.weight(10.0F))

            CuadroInformaciónPersonal()

            Spacer(modifier = Modifier.weight(10.0F))
            AppButton(
                "\uD83D\uDDD1\uFE0F Eliminar cuenta",
                tertiaryContainerLight,
                secondaryContainerLight,
                modifier = Modifier
                    .height(40.dp)
                    .width(180.dp)
            )

            Spacer(modifier = Modifier.weight(20.0F))
        }
        Row(modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            MenuOpciones()
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