# 📚 Plataforma de Reseñas de Materias Universitarias

Aplicación social que permite a estudiantes universitarios calificar, reseñar y descubrir asignaturas de distintas universidades, carreras y facultades. Los usuarios pueden seguir a otros estudiantes, comentar reseñas, dar "Me gusta" y recibir notificaciones sobre la interacción con su contenido, en un formato similar a una red social centrada en la vida académica.

## ✨ Descripción

El sistema busca ayudar a los estudiantes a tomar decisiones informadas sobre qué materias cursar, basándose en las experiencias compartidas por otros usuarios de su misma universidad o carrera. Combina funcionalidades de un catálogo académico (materias, profesores, créditos) con dinámicas de red social (perfiles, seguidores, feed, comentarios, likes y notificaciones).

## 🚀 Funcionalidades principales

### 👤 Gestión de Usuarios
- Registro mediante correo electrónico, nombre de usuario y contraseña.
- Inicio y cierre de sesión, con persistencia de sesión hasta que el usuario decida cerrarla.
- Cambio de contraseña.
- Edición de perfil (información y foto de perfil).
- Eliminación de cuenta.

### 🧑‍🤝‍🧑 Gestión de Perfiles
- Visualización del perfil de cualquier usuario.
- Conteo de seguidores y seguidos.
- Seguir / dejar de seguir a otros usuarios.
- Historial de materias calificadas por un usuario.

### 📖 Gestión de Materias
- Listado de materias disponibles, con búsqueda por nombre.
- Filtros por universidad, carrera y facultad.
- Vista detallada de cada materia: código, universidad asociada, profesores, número de créditos y calificación promedio.

### ⭐ Gestión de Reseñas
- Creación de reseñas por materia, con calificación entera de 0 a 5.
- Texto de review, nivel de dificultad y recomendación (sí/no).
- Restricción de una sola reseña por usuario por materia.
- Edición y eliminación de reseñas propias.
- Listado de todas las reseñas de una materia, con fecha de publicación.

### 💬 Comentarios e Interacciones
- Comentarios en reseñas de otros usuarios, con hilos de respuestas (más de un comentario por reseña/usuario).
- Visualización de todos los comentarios de una reseña.
- "Me gusta" en reseñas y en comentarios, con conteo visible.
- Edición y eliminación de comentarios propios.
- Al eliminar un comentario con respuestas, este se reemplaza por el mensaje **"Comentario eliminado por el usuario"**, conservando las respuestas visibles.

### 📰 Feed Principal
- Feed con las reseñas más recientes de los usuarios seguidos, ordenado por fecha descendente.
- Acceso directo al perfil del autor y al detalle de la materia desde el feed.

### 🔔 Notificaciones
- Notificación al obtener un nuevo seguidor.
- Notificación al recibir un comentario en una reseña.
- Notificación al recibir un "Me gusta" en una reseña o en un comentario.
- Historial de notificaciones.

### ✅ Validaciones
- Formato válido de correo electrónico.
- Campos obligatorios no vacíos (usuario, contraseña, correo).
- Contraseña con requisitos mínimos de seguridad.
- Calificaciones restringidas al rango 0-5.
- Autenticación requerida para acceder a las funcionalidades principales.
