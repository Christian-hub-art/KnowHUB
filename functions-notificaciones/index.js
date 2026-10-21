const { initializeApp } = require("firebase-admin/app");
const { getFirestore } = require("firebase-admin/firestore");
const { getMessaging } = require("firebase-admin/messaging");
const { logger } = require("firebase-functions");
const { onDocumentCreated } = require("firebase-functions/v2/firestore");

initializeApp();

// La app guarda el token con el campo FCMToken de RegisterUserDto;
// Firestore lo puede escribir como "fcmtoken", así que se revisan ambos nombres.
function obtenerToken(user) {
  return user.FCMToken || user.fcmtoken || user.fcmToken || null;
}

async function obtenerUserName(userId) {
  const doc = await getFirestore().collection("users").doc(userId).get();
  return (doc.exists && doc.data().userName) || "Alguien";
}

async function enviarNotificacion(userId, title, body) {
  const userDoc = await getFirestore().collection("users").doc(userId).get();
  if (!userDoc.exists) {
    logger.info("El usuario no existe", { userId });
    return;
  }

  const token = obtenerToken(userDoc.data());
  if (!token) {
    logger.info("El usuario no tiene token FCM", { userId });
    return;
  }

  try {
    await getMessaging().send({
      token: token,
      notification: { title: title, body: body },
    });
    logger.info("Notificación enviada", { userId });
  } catch (error) {
    logger.error("Error enviando la notificación", { userId, error: error.message });
  }
}

exports.notificarNuevoSeguidor = onDocumentCreated(
  "users/{userId}/followers/{followerId}",
  async (event) => {
    const { userId, followerId } = event.params;
    const nombre = await obtenerUserName(followerId);

    await enviarNotificacion(userId, "Nuevo seguidor", `${nombre} empezó a seguirte`);
  }
);

exports.notificarLikeResena = onDocumentCreated(
  "reviews/{reviewId}/likes/{userId}",
  async (event) => {
    const { reviewId, userId } = event.params;

    const reviewDoc = await getFirestore().collection("reviews").doc(reviewId).get();
    if (!reviewDoc.exists) return;

    const review = reviewDoc.data();
    const autorId = review.userId;
    if (!autorId || autorId === userId) return;

    const nombre = await obtenerUserName(userId);
    await enviarNotificacion(
      autorId,
      "Nuevo like",
      `A ${nombre} le gustó tu reseña de ${review.nombreAsignatura || "una materia"}`
    );
  }
);
