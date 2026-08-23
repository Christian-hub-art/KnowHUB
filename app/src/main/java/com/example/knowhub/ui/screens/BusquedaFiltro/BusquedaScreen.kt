package com.example.knowhub.ui.screens.BusquedaFiltro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.knowhub.ui.screens.BusquedaFiltro.Components.BarraFiltro
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.CajaBusqueda

@Composable
fun BusquedaScreen(
    modifier: Modifier = Modifier
){
    var filtro by remember{mutableStateOf("") }

    Box(
        modifier = modifier
    ){
        BackgroundImage()
        BodyBusquedaScreen(
            filtro  ,
            onFiltroChange = {filtro = it},
        )
    }

}


@Composable
fun BodyBusquedaScreen(
    filtro: String,
    onFiltroChange: (String) -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
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
                    onFiltroChange = {onFiltroChange(it)}
                )
            }
            Icon(
                painter = painterResource(id =   R.drawable.buscar),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = tertiaryContainerLight
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = modifier
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
        Box(modifier = modifier
            .background(tertiaryContainerLight)
            .height(2.5F.dp)
            .width(300.dp)
        ) {}
        Column(
            modifier = Modifier
                .width(325.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            CajaBusqueda(
                "15 Nov 2026",
                "1342",
                "Desarrollo Movil",
                "Angarita",
                4,
                60,
                listOf("#Integrales", "#Derivadas", "#Algebra"),
                "Media",
                modifier = Modifier.width(350.dp))
            Spacer(modifier = Modifier.height(15.dp))

            CajaBusqueda(
                "15 Nov 2026",
                "1342",
                "Desarrollo Movil",
                "Angarita",
                4,
                60,
                listOf("#Integrales", "#Derivadas", "#Algebra"),
                "Media",
                modifier = Modifier.width(350.dp))
            Spacer(modifier = Modifier.height(15.dp))
            CajaBusqueda(
                "15 Nov 2026",
                "1342",
                "Desarrollo Movil",
                "Angarita",
                4,
                60,
                listOf("#Integrales", "#Derivadas", "#Algebra"),
                "Media",
                modifier = Modifier.width(350.dp))
            Spacer(modifier = Modifier.height(15.dp)
            )

        }
    }


}


@Composable
@Preview
fun BusquedaScreenPreview(){
    BusquedaScreen()
}