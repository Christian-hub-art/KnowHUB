package com.example.knowhub.data.local

import com.google.firebase.firestore.FirebaseFirestore

object SeedHelper {

    fun seedMaterias(db: FirebaseFirestore) {
        val materias = listOf(
            materia(1, "Desarrollo Movil", "Angarita", 1342, 40, 4, "Alta", listOf("#Computadores")),
            materia(2, "Ecuaciones Diferenciales", "Yesid Lemus", 1355, 90, 5, "Media", listOf("#Integrales", "#Derivadas")),
            materia(3, "Introduccion a la Programación", "Eugenio Derbez", 5655, 20, 0, "Alta", listOf("#Programación")),
            materia(4, "Introduccion a los Sistemas Distribuidos", "Lola Molina", 9101, 100, 3, "Baja", listOf("#Programación", "Sistemas")),
            materia(5, "Cálculo Vectorial", "Alexander Caviedes", 8808, 200, 5, "Media", listOf("#Integrales", "#Derivadas")),
            materia(6, "Cálculo Integral", "Marta Lucía", 8809, 150, 4, "Alta", listOf("#Integrales")),
            materia(7, "Cálculo Diferencial", "Ricardo Reyes", 8810, 120, 5, "Media", listOf("#Derivadas")),
            materia(8, "Física Mecánica", "Juan Pablo", 7701, 80, 3, "Alta", listOf("#Física")),
            materia(9, "Física Electromagnetismo", "Sandra Milena", 7702, 60, 4, "Alta", listOf("#Física")),
            materia(10, "Programación Web", "Carlos Mario", 5656, 45, 5, "Baja", listOf("#Programación")),
            materia(11, "Inglés I", "Teacher Sarah", 2201, 300, 5, "Baja", listOf("#Idiomas")),
            materia(12, "Inglés II", "Teacher John", 2202, 250, 4, "Baja", listOf("#Idiomas"))
        )

        materias.forEach { materia ->
            db.collection("materias").document(materia["id"].toString()).set(materia)
        }
    }

    private fun materia(
        id: Int,
        nombreMateria: String,
        nombreProfesor: String,
        codigoAsignatura: Int,
        cantidadReviews: Int,
        calificacionMedia: Int,
        dificultadMedia: String,
        hashtags: List<String>
    ): Map<String, Any> = mapOf(
        "id" to id,
        "nombreMateria" to nombreMateria,
        "nombreProfesor" to nombreProfesor,
        "codigoAsignatura" to codigoAsignatura,
        "cantidadReviews" to cantidadReviews,
        "calificacionMedia" to calificacionMedia,
        "dificultadMedia" to dificultadMedia,
        "hashtags" to hashtags
    )
}
