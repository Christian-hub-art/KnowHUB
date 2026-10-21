const { initializeApp } = require("firebase-admin/app");
const { FieldValue, getFirestore } = require("firebase-admin/firestore");
const { getMessaging } = require("firebase-admin/messaging");
const { logger } = require("firebase-functions");
const {
  onDocumentCreated,
  onDocumentUpdated,
} = require("firebase-functions/v2/firestore");

initializeApp();

const db = getFirestore();
const messaging = getMessaging();

exports.sendNotification = onDocumentCreated(
  "users/{userId}/notifications/{notificationId}",
  async (event) => {
    const notification = event.data?.data();
    if (!notification) {
      logger.warn("Notification document has no data.", {
        notificationId: event.params.notificationId,
      });
      return;
    }

    const tokens = await db
      .collection("users")
      .doc(event.params.userId)
      .collection("fcmTokens")
      .get();

    for (const document of tokens.docs) {
      await messaging.send({
        token: document.id,
        notification: {
          title: notification.title || "KnowHUB",
          body: notification.body || "Tienes una nueva notificación.",
        },
        data: {
          notificationId: event.params.notificationId,
          type: String(notification.type || ""),
          actorId: String(notification.actorId || ""),
          reviewId: String(notification.reviewId || ""),
          commentId: String(notification.commentId || ""),
        },
      });
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
