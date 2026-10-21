# Reporte de cambios

## Funcionalidad

- El seguimiento mantiene relaciones recíprocas en Firestore; se muestran los conteos y las listas de seguidores/seguidos se pueden abrir desde el perfil.
- El feed de reseñas recibe cambios en tiempo real y permite filtrar por usuarios seguidos.
- Los likes de comentarios siguen la estructura `comments/{commentId}/likes/{userId}`: el ID del documento es el UID y el documento guarda el campo `nombre`, igual que el ejemplo del profesor. Una transacción crea/elimina ese documento y actualiza el contador; calificar reseñas conserva el sistema de estrellas.
- Se guardan notificaciones de nuevos seguidores, comentarios, likes en comentarios y nuevas calificaciones. La pantalla las observa en tiempo real.
- Se agregó la Cloud Function que envía FCM al crearse una notificación y elimina tokens inválidos. Para habilitar push se deben instalar las dependencias de `functions` y desplegar con Firebase CLI indicando el ID del proyecto.
- Las operaciones de creación de comentarios y actualización de contadores/notificaciones se ejecutan en una transacción Firestore.
- Se conservaron los servicios Retrofit, los DTO y los módulos de inyección solicitados, manteniendo Firestore como fuente usada por las funciones sociales actuales.

## Archivos modificados y agregados

**Documentación, configuración y despliegue**

- `.gitignore`
- `README.md`
- `REPORTE_CAMBIOS.md`
- `build.gradle.kts`
- `app/build.gradle.kts`
- `gradle/libs.versions.toml`
- `firebase.json`
- `functions/index.js`
- `functions/package.json`

**Aplicación Android y dominio**

- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/example/knowhub/KnowHUBApp.kt`
- `app/src/main/java/com/example/knowhub/data/Comment.kt`
- `app/src/main/java/com/example/knowhub/data/Notificacion.kt`
- `app/src/main/java/com/example/knowhub/data/Review.kt`
- `app/src/main/res/values/strings.xml`

**Fuentes de datos, DTO, repositorios e inyección**

- `app/src/main/java/com/example/knowhub/data/datasource/CommentRemoteDataSource.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/NotificationRemoteDataSource.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/ReviewRemoteDataSource.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/UserRemoteDataSource.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/dtos/CommentDto.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/dtos/NotificationDto.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/dtos/RegisterUserDto.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/dtos/ReviewDto.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/dtos/UserProfileDto.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/dtos/UserSummaryDto.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/impl/firestore/CommentFirestoreDataSourceImpl.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/impl/firestore/NotificationFirestoreDataSourceImpl.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/impl/firestore/ReviewFirestoreDataSourceImpl.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/impl/firestore/UserFirestoreDataSourceImpl.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/impl/retrofit/CommentRetrofitDataSourceImpl.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/impl/retrofit/ReviewRetrofitDataSourceImpl.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/impl/retrofit/UserRetrofitDataSourceImpl.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/injection/AppModule.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/injection/RemoteDataSourceModule.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/repository/CommentRepository.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/repository/NotificationRepository.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/repository/ReviewRepository.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/repository/UserRepository.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/services/CommentRetrofitService.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/services/MyFirebaseMessagingService.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/services/NotificationTokenStore.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/services/ReviewRetrofitService.kt`
- `app/src/main/java/com/example/knowhub/data/datasource/services/UserRetrofitService.kt`

**Pantallas, navegación y componentes**

- `app/src/main/java/com/example/knowhub/ui/Navegation/AppNavegation.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/BusquedaPerfil/BusquedaPerfilScreen.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/BusquedaPerfil/BusquedaPerfilState.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/BusquedaPerfil/BusquedaPerfilViewModel.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/BusquedaPerfil/Components/CuadrosInformacionPerfil.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/BusquedaPerfil/Components/InformaciónPerfil.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/BusquedaPerfil/Components/MiniCuadroInformacion.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/commentDetail/CommentDetailScreen.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/commentDetail/CommentDetailState.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/commentDetail/CommentDetailViewModel.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/completeReviews/CompleteReviewsScreen.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/completeReviews/CompleteReviewsState.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/completeReviews/CompleteReviewsViewModel.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/completeReviews/components/Review.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/completeSpecificReview/CompleteSpecificReviewScreen.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/completeSpecificReview/CompleteSpecificReviewState.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/completeSpecificReview/CompleteSpecificReviewViewModel.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/completeSpecificReview/components/Comment.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/completeSpecificReview/components/Review.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/connections/ConnectionsScreen.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/connections/ConnectionsState.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/connections/ConnectionsViewModel.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/notifications/NotificationsScreen.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/notifications/NotificationsViewModel.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/notifications/components/CuadroNotificacion.kt`
- `app/src/main/java/com/example/knowhub/ui/screens/profile/ProfileViewModel.kt`
- `app/src/main/java/com/example/knowhub/ui/utils/Components.kt`
- `app/src/main/java/com/example/knowhub/ui/utils/ReviewRatingControl.kt`

## Validación y pendientes operativos

- `git diff --check`: correcto.
- `node --check functions/index.js` y validación JSON de `firebase.json` y `functions/package.json`: correctas.
- No se pudo completar la compilación Android: Gradle no pudo crear o limpiar directorios generados de `app/build` y KSP porque están bloqueados por otro proceso. No se eliminaron esos archivos.
- Las notificaciones push requieren instalar las dependencias de Cloud Functions y desplegar `functions` al proyecto Firebase correspondiente.
