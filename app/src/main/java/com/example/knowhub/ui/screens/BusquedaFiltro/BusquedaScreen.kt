package com.example.knowhub.ui.screens.BusquedaFiltro

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.knowhub.R
import com.example.knowhub.data.GeneralReview
import com.example.knowhub.ui.screens.BusquedaFiltro.Components.BarraFiltro
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.CajaBusqueda
import com.example.knowhub.ui.utils.CajaGeneralBusqueda

@Composable
fun BusquedaScreen(
    generalReviewPressed: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BusquedaViewModel = hiltViewModel() // Se cambia a hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
    ) {
        BackgroundImage()

        BodyBusquedaScreen(
            filtro = uiState.filtro,
            reviews = uiState.reviewsFiltradas,
            isLoading = uiState.isLoading,
            error = uiState.error,
            onFiltroChange = { viewModel.onFiltroChange(it) },
            generalReviewPressed = generalReviewPressed
        )
    }
}

@Composable
fun BodyBusquedaScreen(
    filtro: String,
    onFiltroChange: (String) -> Unit,
    generalReviewPressed: (String) -> Unit,
    reviews: List<GeneralReview>,
    isLoading: Boolean,
    error: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Spacer(modifier = Modifier.height(35.dp))

                Row(
                    modifier = Modifier
                        .width(325.dp)
                        .border(2.dp, tertiaryContainerLight)
                        .background(primaryLight)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        BarraFiltro(
                            filtro = filtro,
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
                    modifier = Modifier
                        .background(tertiaryContainerLight)
                        .height(2.5.dp)
                        .width(300.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.filtros_aplicados),
                    fontSize = 17.sp,
                    fontFamily = BangersFont
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .background(tertiaryContainerLight)
                        .height(2.5.dp)
                        .width(300.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))

                if (isLoading) {
                    CircularProgressIndicator(color = tertiaryContainerLight)
                    Spacer(modifier = Modifier.height(15.dp))
                }

                error?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(16.dp),
                        color = tertiaryContainerLight
                    )
                }
            }

            if (!isLoading && error == null) {
                items(reviews) { review ->
                    CajaGeneralBusqueda(
                        generalReview = review,
                        generalReviewPressed = generalReviewPressed,
                        modifier = Modifier.width(350.dp)
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}



@Composable
@Preview
fun BusquedaScreenPreview() {
    BodyBusquedaScreen(
        filtro = "",
        onFiltroChange = {},
        generalReviewPressed = {},
        reviews = emptyList(),
        isLoading = false,
        error = null
    )
}