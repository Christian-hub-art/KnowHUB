const admin = require("firebase-admin");
const { logger } = require("firebase-functions");
const { onDocumentCreated } = require("firebase-functions/v2/firestore");

admin.initializeApp();

const db = admin.firestore();

exports.sendFollowNotification = onDocumentCreated(
  "users/{userId}/followers/{followerId}",
  async (event) => {
    const userId = event.params.userId;
    const followerId = event.params.followerId;

    const userDoc = await db.collection("users").doc(userId).get();
    const userData = userDoc.data();
    const userToken = userData.fcmToken;

    const followerDoc = await db.collection("users").doc(followerId).get();
    const followerData = followerDoc.data();
    const followerName = followerData.userName;

    if (userToken) {
      const message = {
        token: userToken,
        notification: {
          title: "Nuevo seguidor",
          body: `${followerName} ha comenzado a seguirte.`,
        },
      };

      try {
        await admin.messaging().send(message);
        logger.log(
          `Notificación enviada a ${userId} por nuevo seguidor: ${followerName}`
        );
      } catch (error) {
        logger.error(`Error al enviar notificación a ${userId}:`, error);
      }
    } else {
      logger.log(`El usuario ${userId} no tiene un token FCM válido.`);
    }
  }
);

exports.sendLikeNotification = onDocumentCreated(
  "reviews/{reviewId}/likes/{userId}",
  async (event) => {
    const reviewId = event.params.reviewId;
    const userId = event.params.userId;

    const reviewDoc = await db.collection("reviews").doc(reviewId).get();
    const reviewData = reviewDoc.data();
    const autorId = reviewData.userId;

    if (autorId === userId) return;

    const autorDoc = await db.collection("users").doc(autorId).get();
    const autorData = autorDoc.data();
    const autorToken = autorData.fcmToken;

    const userDoc = await db.collection("users").doc(userId).get();
    const userData = userDoc.data();
    const userName = userData.userName;

    if (autorToken) {
      const message = {
        token: autorToken,
        notification: {
          title: "Nuevo like",
          body: `A ${userName} le gustó tu reseña de ${reviewData.nombreAsignatura}.`,
        },
      };

      try {
        await admin.messaging().send(message);
        logger.log(
          `Notificación enviada a ${autorId} por like de: ${userName}`
        );
      } catch (error) {
        logger.error(`Error al enviar notificación a ${autorId}:`, error);
      }
    } else {
      logger.log(`El usuario ${autorId} no tiene un token FCM válido.`);
    }
  }
);
