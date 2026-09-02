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
import com.example.knowhub.data.GeneralReview
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
//Pantalla de inicio de la aplicación que despliega las categorías dinámicas de materias.
@Composable
fun InicioScreen(
    inicioViewModel: InicioViewModel,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by inicioViewModel.uiState.collectAsState()
    Box(modifier = modifier) {
        BackgroundImage()
        BodyInicioScreen(
            allGeneralReviews = state.allGeneralReviews,
            onSeeAllClick = onSeeAllClick
        )
    }
}
//Estructura y procesamiento de contenido para la pantalla de inicio.
@Composable
fun BodyInicioScreen(
    allGeneralReviews: List<GeneralReview>,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 1. Extraer palabras significativas y agrupar materias
    val stopWords = setOf("a", "de", "la", "en", "el", "los", "las", "y", "o", "con", "por", "para", "un", "una", "i", "ii", "iii")

    val wordsToMaterias = mutableMapOf<String, MutableList<GeneralReview>>()

    allGeneralReviews.forEach { review ->
        val words = review.nombreMateria.split(" ")
            .map { it.lowercase().filter { c -> c.isLetterOrDigit() } }
            .filter { it.isNotEmpty() && it !in stopWords }

        words.forEach { word ->
            wordsToMaterias.getOrPut(word) { mutableListOf() }.add(review)
        }
    }

    // 2. Filtrar palabras que aparecen en al menos 2 materias y ordenar alfabéticamente por la palabra clave
    val dynamicCategories = wordsToMaterias.filter { it.value.size >= 2 }
        .mapKeys { it.key.uppercase() }
        .toList()
        .sortedBy { it.first }

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

        // 3. Crear una CajaReseñas por cada categoría dinámica
        itemsIndexed(
            items = dynamicCategories,
            key = { _, categoria -> categoria.first }
        ) { index, (category, materias) ->
            val isEven = index % 2 == 0
            CajaReseñas(
                temaGeneral = category,
                materias = materias.distinctBy { it.id }.map { review ->
                    MateriaResumida(
                        calificacion = review.calificacionMedia,
                        nombreMateria = review.nombreMateria,
                        profesor = review.nombreProfesor,
                        numeroResenas = review.cantidadReviews
                    )
                },
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
