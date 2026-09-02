package com.example.knowhub.ui.screens.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.knowhub.R
import com.example.knowhub.data.MateriaResumida
import com.example.knowhub.ui.screens.inicio.components.CajaReseñas
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.onTertiaryContainerLight
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun InicioScreen(
    inicioViewModel: InicioViewModel,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by inicioViewModel.uiState.collectAsState()
    Box(modifier = modifier) {
        BackgroundImage()
        BodyInicioScreen(
            categories = state.categories,
            onSeeAllClick = onSeeAllClick
        )
    }
}

@Composable
fun BodyInicioScreen(
    categories: List<Pair<String, List<MateriaResumida>>>,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                AppLabel(
                    stringResource(R.string.bienvenido),
                    colorResource(R.color.blancoKnowHUB),
                    colorResource(R.color.NegroKnowHUB),
                    modifier = Modifier.height(35.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = stringResource(R.string.explora_materias),
                    color = onTertiaryContainerLight,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ArvoFont
                )
            }
        }

        // Crear una CajaReseñas por cada categoría
        itemsIndexed(
            items = categories,
            // cada item de va a distinguir por su nombre de categoria
            key = { _, categoria -> categoria.first }
        )
        /* index es la posicion del item actual en la lista de items
        *  category: "calculo" materias= [Calculo I,Calculo II]  */
        { index, (category, materias) ->
            val isEven = (index % 2 == 0)
            CajaReseñas(
                temaGeneral = category,
                materias = materias,
                // isEven se usa para alternar los colores de las CajasReseñas (azul/amarillo)
                colorTexto = if (isEven) primaryLight else tertiaryContainerLight,
                colorCaja = if (isEven) primaryContainerLight else secondaryContainerLight,
                onSeeAllClick = onSeeAllClick,
                modifier = Modifier.height(240.dp)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun InicioScreenPreview() {
    InicioScreen(
        inicioViewModel = viewModel(),
        onSeeAllClick = {}
    )
}
