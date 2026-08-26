package com.example.knowhub.ui.screens.BusquedaFiltro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.data.local.LocalNotificacionProvider
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.ui.screens.BusquedaFiltro.Components.BarraFiltro
import com.example.knowhub.ui.screens.notifications.components.CuadroNotificacion
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.CajaBusqueda

@Composable
fun BusquedaScreen(
    generalReviewPressed: (Int) -> Unit,
    modifier: Modifier = Modifier,

){
    var filtro by remember{mutableStateOf("") }

    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyBusquedaScreen(
            filtro  ,
            onFiltroChange = {filtro = it},
            generalReviewPressed = generalReviewPressed
        )
    }

}


@Composable
fun BodyBusquedaScreen(
    filtro: String,
    onFiltroChange: (String) -> Unit,
    generalReviewPressed: (Int) -> Unit,
    modifier: Modifier = Modifier,
){

    val allGeneralReviews = localGeneralReviewProvider.generalReviews
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {

            item() {
                Spacer(modifier = Modifier.height(35.dp))

                Row(
                    modifier = Modifier
                        .width(325.dp)
                        .border(2.dp, tertiaryContainerLight)
                        .background(primaryLight)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        BarraFiltro(
                            filtro,
                            onFiltroChange = { onFiltroChange(it) }
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.buscar),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = tertiaryContainerLight
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = modifier
                        .background(tertiaryContainerLight)
                        .height(2.5F.dp)
                        .width(300.dp)
                ) {}
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    stringResource(R.string.filtros_aplicados),
                    fontSize = 17.sp,
                    fontFamily = BangersFont
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = modifier
                        .background(tertiaryContainerLight)
                        .height(2.5F.dp)
                        .width(300.dp)
                ) {}

                Spacer(modifier = Modifier.height(10.dp))
            }
            items(allGeneralReviews.size) { index ->
                CajaBusqueda(
                    allGeneralReviews[index],
                    generalReviewPressed = generalReviewPressed,
                    modifier = Modifier.width(350.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

    }
}


@Composable
@Preview
fun BusquedaScreenPreview(){
    BusquedaScreen(generalReviewPressed = {})
}

