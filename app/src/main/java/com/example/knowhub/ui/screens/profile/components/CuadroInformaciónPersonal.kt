package com.example.knowhub.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.ArvoFont
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.AppLabel

@Composable
fun CuadroInformaciónPersonal(
    nombre: String,
    correo: String,
    isAuthenticated: Boolean,
    modificarDatosPressed: () -> Unit,
    eliminarCuentaPressed: () -> Unit,
    iniciarSesionPressed: () -> Unit,
    registrarsePressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, shape)
            .clip(shape)
            .background(
                if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.surface
                } else {
                    colorResource(R.color.blancoKnowHUB)
                }
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline,
                shape
            )
            .padding(18.dp)
    ) {
        AppLabel(
            stringResource(R.string.informacion_personal_header),
            colorResource(R.color.blancoKnowHUB),
            colorResource(R.color.NegroKnowHUB),
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
        )

        if (isAuthenticated) {
            ProfileDataRow(
                label = stringResource(R.string.nombre_de_usuario),
                value = nombre,
                modifier = Modifier.padding(top = 18.dp)
            )
            ProfileDataRow(
                label = stringResource(R.string.correo_electr_nico),
                value = correo,
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            AppButton(
                stringResource(R.string.modificar_datos),
                colorResource(R.color.blancoKnowHUB),
                colorResource(R.color.AzulKnowHUB),
                onClick = modificarDatosPressed,
                modifier = Modifier
                    .height(42.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))
            AppButton(
                stringResource(R.string.eliminar_cuenta),
                colorResource(R.color.NegroKnowHUB),
                colorResource(R.color.AmarilloKnowHUB),
                onClick = eliminarCuentaPressed,
                modifier = Modifier
                    .height(42.dp)
                    .fillMaxWidth()
            )
        } else {
            Spacer(modifier = Modifier.height(18.dp))
            AppButton(
                stringResource(R.string.iniciar_sesion),
                colorResource(R.color.blancoKnowHUB),
                colorResource(R.color.AzulKnowHUB),
                onClick = iniciarSesionPressed,
                modifier = Modifier
                    .height(42.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            AppButton(
                stringResource(R.string.registrarse),
                colorResource(R.color.blancoKnowHUB),
                colorResource(R.color.AzulKnowHUB),
                onClick = registrarsePressed,
                modifier = Modifier
                    .height(42.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ProfileDataRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontFamily = ArvoFont,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            fontFamily = ArvoFont,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
