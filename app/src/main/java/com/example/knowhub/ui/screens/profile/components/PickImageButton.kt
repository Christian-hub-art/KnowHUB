package com.example.knowhub.ui.screens.profile.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.R


@Composable
fun PickImageButton(
    action: (uri:Uri) -> Unit,
    modifier: Modifier = Modifier
){
    val launcher = rememberLauncherForActivityResult(
        contract =
            ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("ProfileScreen", uri .toString())
            action(uri)
        }
    }

    AppButton(
        stringResource(R.string.subir_foto),
        colorResource(R.color.NegroKnowHUB),
        colorResource(R.color.blancoKnowHUB),
        onClick = {
            launcher.launch("image/*")
        },
        modifier = Modifier
            .height(30.dp)
            .width(180.dp)

    )


}