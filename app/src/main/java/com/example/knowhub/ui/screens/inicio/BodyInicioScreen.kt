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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.data.MateriaResumida
import com.example.knowhub.ui.screens.inicio.components.CajaReseñas
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.theme.BangersFont
import com.example.knowhub.ui.theme.onTertiaryContainerLight
import com.example.knowhub.ui.theme.primaryContainerLight
import com.example.knowhub.ui.theme.primaryLight
import com.example.knowhub.ui.theme.secondaryContainerLight
import com.example.knowhub.ui.theme.tertiaryContainerLight
import com.example.knowhub.ui.utils.AppLabel
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun InicioScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        BackgroundImage()
        BodyInicioScreen()
    }
}

@Composable
fun BodyInicioScreen(
    modifier: Modifier = Modifier
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
                    "Bienvenido",
                    colorResource(R.color.blancoKnowHUB),
                    colorResource(R.color.NegroKnowHUB),
                    modifier = Modifier.height(35.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Explora materias y descubre reseñas de otros estudiantes:",
                    color = onTertiaryContainerLight,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ArvoFont
                )
            }
        }
        item {
            CajaReseñas(
                temaGeneral = "CÁLCULO",
                materias = listOf(
                    MateriaResumida(
                        calificacion = 5,
                        nombreMateria = "Cálculo I",
                        profesor = "Angarita",
                        numeroResenas = 14
                    ),
                    MateriaResumida(
                        calificacion = 4,
                        nombreMateria = "Cálculo II",
                        profesor = "Prof. Vega",
                        numeroResenas = 8
                    ),
                    MateriaResumida(
                        calificacion = 3,
                        nombreMateria = "Ecuaciones",
                        profesor = "Dra. Gómez",
                        numeroResenas = 5
                    )
                ),
                colorTexto = primaryLight,
                colorCaja = primaryContainerLight,
                onSeeAllClick = { /* Navegación */ }
            )}
        item {
            CajaReseñas(
                temaGeneral = "PROGRAMACIÓN",
                materias = listOf(
                    MateriaResumida(
                        calificacion = 5,
                        nombreMateria = "Desarrollo Móvil",
                        profesor = "Angarita",
                        numeroResenas = 18
                    ),
                    MateriaResumida(
                        calificacion = 4,
                        nombreMateria = "Estructura Datos",
                        profesor = "Ing. Ruiz",
                        numeroResenas = 11
                    ),
                    MateriaResumida(
                        calificacion = 5,
                        nombreMateria = "Programación I",
                        profesor = "Lic. Hoyos",
                        numeroResenas = 22
                    )
                ),
                colorTexto = tertiaryContainerLight,
                colorCaja = secondaryContainerLight,
                onSeeAllClick = { /* Navegación */ }
            )}
        item{
            CajaReseñas(
                temaGeneral = "IDIOMAS",
                materias = listOf(
                    MateriaResumida(
                        calificacion = 3,
                        nombreMateria = "Portugués",
                        profesor = "Torres",
                        numeroResenas = 18
                    ),
                    MateriaResumida(
                        calificacion = 4,
                        nombreMateria = "Inglés Avanzado",
                        profesor = "Ing. Ruiz",
                        numeroResenas = 11
                    ),
                    MateriaResumida(
                        calificacion = 1,
                        nombreMateria = "Francés 3",
                        profesor = "Lic. Camilo",
                        numeroResenas = 22
                    )
                ),
                colorTexto = primaryLight,
                colorCaja = primaryContainerLight,
                onSeeAllClick = { /* Navegación */ }
            )
        }
        item {
            CajaReseñas(
                temaGeneral = "CÁLCULO",
                materias = listOf(
                    MateriaResumida(
                        calificacion = 5,
                        nombreMateria = "Cálculo I",
                        profesor = "Angarita",
                        numeroResenas = 14
                    ),
                    MateriaResumida(
                        calificacion = 4,
                        nombreMateria = "Cálculo II",
                        profesor = "Prof. Vega",
                        numeroResenas = 8
                    ),
                    MateriaResumida(
                        calificacion = 3,
                        nombreMateria = "Ecuaciones",
                        profesor = "Dra. Gómez",
                        numeroResenas = 5
                    )
                ),
                colorTexto = tertiaryContainerLight,
                colorCaja = secondaryContainerLight,
                onSeeAllClick = { /* Navegación */ }
            )
        }
        item {
            CajaReseñas(
                temaGeneral = "PROGRAMACIÓN",
                materias = listOf(
                    MateriaResumida(
                        calificacion = 5,
                        nombreMateria = "Desarrollo Móvil",
                        profesor = "Angarita",
                        numeroResenas = 18
                    ),
                    MateriaResumida(
                        calificacion = 4,
                        nombreMateria = "Estructura Datos",
                        profesor = "Ing. Ruiz",
                        numeroResenas = 11
                    ),
                    MateriaResumida(
                        calificacion = 5,
                        nombreMateria = "Programación I",
                        profesor = "Lic. Hoyos",
                        numeroResenas = 22
                    )
                ),
                colorTexto = primaryLight,
                colorCaja = primaryContainerLight,
                onSeeAllClick = { /* Navegación */ }
            )
        }
        item{
            CajaReseñas(
                temaGeneral = "IDIOMAS",
                materias = listOf(
                    MateriaResumida(
                        calificacion = 3,
                        nombreMateria = "Portugués",
                        profesor = "Torres",
                        numeroResenas = 18
                    ),
                    MateriaResumida(
                        calificacion = 4,
                        nombreMateria = "Inglés Avanzado",
                        profesor = "Ing. Ruiz",
                        numeroResenas = 11
                    ),
                    MateriaResumida(
                        calificacion = 1,
                        nombreMateria = "Francés 3",
                        profesor = "Lic. Camilo",
                        numeroResenas = 22
                    )
                ),
                colorTexto = tertiaryContainerLight,
                colorCaja = secondaryContainerLight,
                onSeeAllClick = { /* Navegación */ }
            )}
    }
}

@Composable
@Preview(showSystemUi = true)
fun InicioScreenPreview() {
    InicioScreen()
}
