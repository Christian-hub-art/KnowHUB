package com.example.knowhub.data

import java.util.Date

data class Notificacion(
    val idNotificacion: Int,
    val leida: Boolean,
    val fecha: Date,
    val mensaje :String
)
