package com.example.knowhub.data.local

import com.example.knowhub.data.Notificacion
import java.util.Date

object LocalNotificacionProvider {
    val notificaciones = listOf(
        Notificacion(
            idNotificacion = 1,
            leida = false,
            fecha = Date(),
            mensaje = "Tienes una nueva solicitud de amistad."
        ),
        Notificacion(
            idNotificacion = 2,
            leida = false,
            fecha = Date(),
            mensaje = "Carlos respondió a tu publicación."
        ),
        Notificacion(
            idNotificacion = 3,
            leida = true,
            fecha = Date(),
            mensaje = "Tu contraseña fue actualizada correctamente."
        ),
        Notificacion(
            idNotificacion = 4,
            leida = false,
            fecha = Date(),
            mensaje = "Tienes una nueva calificación en Matemáticas."
        ),
        Notificacion(
            idNotificacion = 5,
            leida = true,
            fecha = Date(),
            mensaje = "Ana comenzó a seguirte."
        ),
        Notificacion(
            idNotificacion = 6,
            leida = false,
            fecha = Date(),
            mensaje = "Se publicó una nueva actividad en Programación."
        ),
        Notificacion(
            idNotificacion = 7,
            leida = false,
            fecha = Date(),
            mensaje = "Tu profesor ha dejado un comentario en tu entrega."
        ),
        Notificacion(
            idNotificacion = 8,
            leida = true,
            fecha = Date(),
            mensaje = "Tu solicitud para unirte al grupo fue aceptada."
        ),
        Notificacion(
            idNotificacion = 9,
            leida = false,
            fecha = Date(),
            mensaje = "Tienes un nuevo mensaje de Laura."
        ),
        Notificacion(
            idNotificacion = 10,
            leida = true,
            fecha = Date(),
            mensaje = "La materia Bases de Datos ha sido agregada a tus favoritos."
        ),
        Notificacion(
            idNotificacion = 11,
            leida = false,
            fecha = Date(),
            mensaje = "Recuerda entregar tu actividad antes del viernes."
        ),
        Notificacion(
            idNotificacion = 12,
            leida = false,
            fecha = Date(),
            mensaje = "Se ha creado un nuevo grupo de estudio."
        ),
        Notificacion(
            idNotificacion = 13,
            leida = true,
            fecha = Date(),
            mensaje = "Tu publicación recibió 10 nuevos me gusta."
        ),
        Notificacion(
            idNotificacion = 14,
            leida = false,
            fecha = Date(),
            mensaje = "Pedro comentó en la publicación que estás siguiendo."
        ),
        Notificacion(
            idNotificacion = 15,
            leida = false,
            fecha = Date(),
            mensaje = "Hay una nueva recomendación de materia para ti."
        )
    )
}