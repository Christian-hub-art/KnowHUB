package com.example.knowhub.ui.screens.BusquedaFiltro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.data.local.localGeneralReviewProvider
import com.example.knowhub.ui.screens.BusquedaFiltro.Components.BarraFiltro
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.CajaBusqueda
//Pantalla principal de búsqueda y filtrado de reseñas generales.
// Conecta la UI con el ViewModel para seguir el patrón de arquitectura MVVM.

@Composable
fun BusquedaScreen(
    generalReviewPressed: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BusquedaViewModel = viewModel()
){
    // Subscripción reactiva al estado expuesto por el ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
    ){
        // Fondo decorativo reutilizable de la aplicación
        BackgroundImage()
        // Contenido principal de la pantalla desacoplado de la lógica de negocio
        BodyBusquedaScreen(
            filtro = uiState.filtro,
            reviews = uiState.reviews,
            onFiltroChange = { viewModel.onFiltroChange(it) },
            generalReviewPressed = generalReviewPressed
        )
    }
}
//Composable que define la estructura visual y los elementos interactivos de la pantalla de búsqueda.
@Composable
fun BodyBusquedaScreen(
    filtro: String,
    onFiltroChange: (String) -> Unit,
    generalReviewPressed: (Int) -> Unit,
    modifier: Modifier = Modifier,
    reviews: List<GeneralReview> = localGeneralReviewProvider.generalReviews
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) { // Lista de desplazamiento vertical optimizada para mostrar los elementos
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {

            item() {
                Spacer(modifier = Modifier.height(35.dp))
                // Contenedor del campo de entrada y el ícono de búsqueda
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
            // Renderizado dinámico de la lista de reseñas
            items(reviews.size) { index ->
                CajaBusqueda(
                    reviews[index],
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