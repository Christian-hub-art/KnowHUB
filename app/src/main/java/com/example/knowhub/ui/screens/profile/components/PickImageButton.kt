package com.example.knowhub.ui.screens.profile.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.knowhub.R
import com.example.knowhub.ui.theme.primaryContainerLight


@Composable
fun PickImageButton(
    action: (uri:Uri) -> Unit,
    modifier: Modifier = Modifier,
    isUploading: Boolean = false
){
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                Log.w("ProfileScreen", "No se pudo conservar el permiso de lectura de la imagen", e)
            }
            action(uri)
        }
    }

    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = primaryContainerLight,
        shadowElevation = 4.dp
    ) {
        IconButton(
            onClick = { launcher.launch(arrayOf("image/*")) },
            enabled = !isUploading,
            modifier = Modifier.size(40.dp)
        ) {
            if (isUploading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.subir_foto),
                    tint = Color.White
                )
            }
        }
    }
}