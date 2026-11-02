package com.example.knowhub.data.local

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

object SeedHelper {

    fun seedMaterias(db: FirebaseFirestore) {
        val materias = listOf(
            materia(1, "Desarrollo Movil", "Angarita", 1342, "Alta", listOf("#Computadores")),
            materia(2, "Ecuaciones Diferenciales", "Yesid Lemus", 1355, "Media", listOf("#Integrales", "#Derivadas")),
            materia(3, "Introduccion a la Programación", "Eugenio Derbez", 5655, "Alta", listOf("#Programación")),
            materia(4, "Introduccion a los Sistemas Distribuidos", "Lola Molina", 9101, "Baja", listOf("#Programación", "Sistemas")),
            materia(5, "Cálculo Vectorial", "Alexander Caviedes", 8808, "Media", listOf("#Integrales", "#Derivadas")),
            materia(6, "Cálculo Integral", "Marta Lucía", 8809, "Alta", listOf("#Integrales")),
            materia(7, "Cálculo Diferencial", "Ricardo Reyes", 8810, "Media", listOf("#Derivadas")),
            materia(8, "Física Mecánica", "Juan Pablo", 7701, "Alta", listOf("#Física")),
            materia(9, "Física Electromagnetismo", "Sandra Milena", 7702, "Alta", listOf("#Física")),
            materia(10, "Programación Web", "Carlos Mario", 5656, "Baja", listOf("#Programación")),
            materia(11, "Inglés I", "Teacher Sarah", 2201, "Baja", listOf("#Idiomas")),
            materia(12, "Inglés II", "Teacher John", 2202, "Baja", listOf("#Idiomas"))
        )

        // merge: no borra cantidadReviews, sumaCalificaciones ni calificacionMedia,
        // que se actualizan al crear, editar o borrar reseñas
        materias.forEach { materia ->
            db.collection("materias").document(materia["id"].toString()).set(materia, SetOptions.merge())
        }
    }

    private fun materia(
        id: Int,
        nombreMateria: String,
        nombreProfesor: String,
        codigoAsignatura: Int,
        dificultadMedia: String,
        hashtags: List<String>
    ): Map<String, Any> = mapOf(
        "id" to id,
        "nombreMateria" to nombreMateria,
        "nombreProfesor" to nombreProfesor,
        "codigoAsignatura" to codigoAsignatura,
        "dificultadMedia" to dificultadMedia,
        "hashtags" to hashtags
    )
}
