const admin = require("firebase-admin");
const { FieldValue } = require("firebase-admin/firestore");
const {
  onDocumentCreated,
  onDocumentUpdated,
} = require("firebase-functions/v2/firestore");
const { logger } = require("firebase-functions");

admin.initializeApp();

const db = admin.firestore();

exports.sendFollowNotification = onDocumentCreated(
  "users/{userId}/followers/{followerId}",
  async (event) => {
    const { userId, followerId } = event.params;

    const userDoc = await db.collection("users").doc(userId).get();
    const followerDoc = await db.collection("users").doc(followerId).get();

    if (!userDoc.exists || !followerDoc.exists) {
      return;
    }

    const userData = userDoc.data();
    const followerData = followerDoc.data();
    const userToken = userData.fcmToken;

    if (!userToken) {
      logger.log(`El usuario ${userId} no tiene un token FCM válido.`);
      return;
    }

    const message = {
      token: userToken,
      notification: {
        title: "Nuevo seguidor",
        body: `${followerData.userName || "Un usuario"} ha comenzado a seguirte.`,
      },
    };

    try {
      await admin.messaging().send(message);
      logger.log(`Notificación enviada a ${userId} por nuevo seguidor.`);
    } catch (error) {
      logger.error(`Error al enviar notificación a ${userId}:`, error);
    }
  }
);

exports.sendLikeNotification = onDocumentCreated(
  "reviews/{reviewId}/likes/{userId}",
  async (event) => {
    const { reviewId, userId } = event.params;

    const reviewDoc = await db.collection("reviews").doc(reviewId).get();
    if (!reviewDoc.exists) {
      return;
    }

    const reviewData = reviewDoc.data();
    const authorId = reviewData.userId;

    if (!authorId || authorId === userId) {
      return;
    }

    const authorDoc = await db.collection("users").doc(authorId).get();
    const userDoc = await db.collection("users").doc(userId).get();

    if (!authorDoc.exists || !userDoc.exists) {
      return;
    }

    const authorToken = authorDoc.get("fcmToken");
    if (!authorToken) {
      logger.log(`El usuario ${authorId} no tiene un token FCM válido.`);
      return;
    }

    const message = {
      token: authorToken,
      notification: {
        title: "Nuevo like",
        body: `A ${userDoc.get("userName") || "Un usuario"} le gustó tu reseña de ${reviewData.nombreAsignatura}.`,
      },
    };

    try {
      await admin.messaging().send(message);
      logger.log(`Notificación enviada a ${authorId} por un nuevo like.`);
    } catch (error) {
      logger.error(`Error al enviar notificación a ${authorId}:`, error);
    }
  }
);

exports.notifyReviewLike = onDocumentCreated(
  "reviews/{reviewId}/likes/{userId}",
  async (event) => {
    const { reviewId, userId } = event.params;
    const review = await db.collection("reviews").doc(reviewId).get();
    const ownerId = review.get("userId");

    if (!review.exists || !ownerId || ownerId === userId) {
      return;
    }

    const liker = await db.collection("users").doc(userId).get();
    const likerName = liker.get("name") || liker.get("userName") || "Un usuario";

    await db
      .collection("users")
      .doc(ownerId)
      .collection("notifications")
      .doc(event.id)
      .set({
        type: "like",
        title: "Me gusta en tu reseña",
        body: `${likerName} indicó que le gusta tu reseña.`,
        actorId: userId,
        reviewId,
        createdAt: FieldValue.serverTimestamp(),
        read: false,
      });
  }
);

exports.updateUserInfoInReviews = onDocumentUpdated(
  "users/{userId}",
  async (event) => {
    const before = event.data.before.data();
    const after = event.data.after.data();
    const update = {};

    if (before.name !== after.name) {
      update["user.name"] = after.name;
    }
    if (before.userName !== after.userName) {
      update["user.userName"] = after.userName;
    }
    if (before.foto !== after.foto) {
      update["user.profileImage"] = after.foto;
    }

    if (Object.keys(update).length === 0) {
      return;
    }

    const reviews = await db
      .collection("reviews")
      .where("userId", "==", event.params.userId)
      .get();

    for (const review of reviews.docs) {
      await review.ref.update(update);
    }
  }
);
