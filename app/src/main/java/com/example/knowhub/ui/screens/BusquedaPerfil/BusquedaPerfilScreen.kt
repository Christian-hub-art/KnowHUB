package com.example.knowhub.ui.screens.BusquedaPerfil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.BusquedaPerfil.Components.InformacionPerfil
import com.example.knowhub.ui.screens.BusquedaPerfil.Components.MiniCuadroInformacion
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.theme.tertiaryLight
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.AppLabelBig
import com.example.knowhub.ui.utils.BackgroundImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.CajaBusqueda

@Composable
fun BusquedaPerfilScreen(
    modifier: Modifier = Modifier
){

    Box(
        modifier=modifier
    ){
        BackgroundImage()
        BodyBusquedaPerfilScrenn(
            onClick = {}
        )
    }


}

@Composable
fun BodyBusquedaPerfilScrenn(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        AppLabelBig(
            "Laura",
            colorTexto = primaryLight,
            color = tertiaryContainerLight,
            modifier = Modifier
                .width(350.dp)
                .height(45.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        InformacionPerfil()

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = modifier.width(320.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.School,
                contentDescription = "Icono graduado",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Ingenieria de Sistemas",
                fontFamily = BangersFont
            )
            Spacer(modifier = Modifier.weight(1f))
            AppButton(
                "Seguir",
                colorBoton = primaryContainerLight,
                onClick = onClick
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        AppLabel(
            "Reseñas Publicadas",
            colorTexto = tertiaryContainerLight,
            color = primaryLight,
            modifier = Modifier
                .width(350.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        CajaBusqueda(
            localGeneralReviewProvider.generalReviews[1],
            {/*TODO*/},
            modifier = Modifier.width(350.dp))
        Spacer(modifier = Modifier.height(15.dp))

        CajaBusqueda(
            localGeneralReviewProvider.generalReviews[2],
            {/*TODO*/},
            modifier = Modifier.width(350.dp))



    }
}





@Composable
@Preview
fun BusquedaPerfilScreenReview(){
    BusquedaPerfilScreen()
}