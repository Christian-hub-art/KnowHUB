const { initializeApp } = require("firebase-admin/app");
const { getFirestore } = require("firebase-admin/firestore");
const { getMessaging } = require("firebase-admin/messaging");
const { onDocumentCreated } = require("firebase-functions/v2/firestore");

initializeApp();

const db = getFirestore();

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

    if (userToken) {
      const payload = {
        token: userToken,
        notification: {
          title: "Nuevo seguidor",
          body: `${followerData.userName} empezó a seguirte`,
        },
      };

      await getMessaging().send(payload);
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

    if (autorToken) {
      const payload = {
        token: autorToken,
        notification: {
          title: "Nuevo like",
          body: `A ${userData.userName} le gustó tu reseña de ${reviewData.nombreAsignatura}`,
        },
      };

      await getMessaging().send(payload);
    }
  }
);
