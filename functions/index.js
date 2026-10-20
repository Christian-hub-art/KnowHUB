const { initializeApp } = require("firebase-admin/app");
const { getFirestore } = require("firebase-admin/firestore");
const { getMessaging } = require("firebase-admin/messaging");
const { logger } = require("firebase-functions");
const { onDocumentCreated } = require("firebase-functions/v2/firestore");

initializeApp();

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

    const userId = event.params.userId;
    const tokenSnapshot = await getFirestore()
      .collection("users")
      .doc(userId)
      .collection("fcmTokens")
      .get();
    const tokens = tokenSnapshot.docs.map((document) => document.id).filter(Boolean);

    if (tokens.length === 0) {
      logger.info("User has no registered FCM tokens.", { userId });
      return;
    }

    for (let start = 0; start < tokens.length; start += 500) {
      const batch = tokens.slice(start, start + 500);
      const response = await getMessaging().sendEachForMulticast({
        tokens: batch,
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

      const invalidTokens = [];
      response.responses.forEach((result, index) => {
        if (
          !result.success &&
          (result.error?.code === "messaging/registration-token-not-registered" ||
            result.error?.code === "messaging/invalid-registration-token")
        ) {
          invalidTokens.push(batch[index]);
        }
      });

      if (invalidTokens.length > 0) {
        const firestore = getFirestore();
        const deletions = invalidTokens.map((token) =>
          firestore.collection("users").doc(userId).collection("fcmTokens").doc(token).delete()
        );
        await Promise.all(deletions);
      }

      logger.info("FCM notification processed.", {
        userId,
        successCount: response.successCount,
        failureCount: response.failureCount,
      });
    }
  }
);
