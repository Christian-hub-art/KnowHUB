package com.example.knowhub.ui.screens.register

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowhub.R
import com.example.knowhub.ui.screens.register.components.SignUPMessage
import com.example.knowhub.ui.screens.register.components.TextosField
import com.example.knowhub.ui.theme.*
import com.example.knowhub.ui.utils.AppButton
import com.example.knowhub.ui.utils.BackgroundImage
import com.example.knowhub.ui.utils.LogoApp
import com.example.knowhub.ui.utils.BackgroundImage

@Composable
fun RegisterScreen(
    registrarseSesionButtonPressed: () -> Unit,
    loginButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(){
        BackgroundImage()
        var nombre by remember{mutableStateOf("") }
        var correoElectronico by remember { mutableStateOf("") }
        var nombreUsuario  by remember { mutableStateOf("") }
        var contrasena  by remember { mutableStateOf("") }
        var confirmarContrasena  by remember { mutableStateOf("") }
        BodyRegisterScreen(
            nombre,
            correoElectronico,
            nombreUsuario,
            contrasena,
            confirmarContrasena,
            onNombreChange ={nombre= it},
            onCorreoElectronicoChange ={correoElectronico= it},
            onNombreUsuarioChange={nombreUsuario= it},
            onContrasenaChange={contrasena= it},
            onConfirmarContrasenaChange={confirmarContrasena= it},
            registrarseSesionButtonPressed = registrarseSesionButtonPressed,
            loginButtonPressed = loginButtonPressed
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterScreenPreview(){
    RegisterScreen(
        registrarseSesionButtonPressed = {},
        loginButtonPressed = {}
    )
}

@Composable
fun BodyRegisterScreen(
    nombre: String,
    correoElectronico: String,
    nombreUsuario: String,
    contrasena: String,
    confirmarContrasena: String,
    onNombreChange: (String) ->Unit,
    onCorreoElectronicoChange: (String) ->Unit,
    onNombreUsuarioChange: (String) ->Unit,
    onContrasenaChange: (String) ->Unit,
    onConfirmarContrasenaChange: (String) ->Unit,
    registrarseSesionButtonPressed: () -> Unit,
    loginButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoApp()
        SignUPMessage()
        Spacer(modifier = Modifier.weight(1.0f))



        TextosField(nombre,
            correoElectronico,
            nombreUsuario,
            contrasena,
            confirmarContrasena,
            onNombreChange ={onNombreChange(it)},
            onCorreoElectronicoChange ={onCorreoElectronicoChange(it)},
            onNombreUsuarioChange={onNombreUsuarioChange(it)},
            onContrasenaChange={onContrasenaChange(it)},
            onConfirmarContrasenaChange={onConfirmarContrasenaChange(it)})

        Spacer(modifier = Modifier.weight(10.0F))

        AppButton(
            "Registrare",
            primaryLight,
            tertiaryContainerLight,
            onClick = {
                registrarseSesionButtonPressed()
            },
                modifier = modifier
                    .height(30.dp)
                    .width(280.dp)
        )

        if (contrasena.length < 6)
            Text("La contraseña debe tener al menos 6 carcateres",
                fontFamily = BangersFont)
        if(contrasena != confirmarContrasena)
            Text("Las contraseñas no coinciden",
                fontFamily = BangersFont)

        Spacer(modifier = Modifier.weight(10.0F))


        TextButton(
            onClick = {
                loginButtonPressed()
            }
        ) {
            Text(
                text = "¿Ya tienes cuentas?  Login",
                fontFamily = BangersFont,
                color = tertiaryContainerLight
            )
        }

        Spacer(modifier = Modifier.weight(1.0f))

    }
}






