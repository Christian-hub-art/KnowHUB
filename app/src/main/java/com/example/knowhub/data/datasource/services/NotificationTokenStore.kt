package com.example.knowhub.data.datasource.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

object NotificationTokenStore {
    fun registerCurrentToken() {
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener(::save)
            .addOnFailureListener { error ->
                Log.e(TAG, "Could not retrieve the Firebase Messaging token.", error)
            }
    }

    fun save(token: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId.isNullOrBlank()) {
            Log.i(TAG, "Skipping FCM token registration because no user is signed in.")
            return
        }

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("fcmTokens")
            .document(token)
            .set(mapOf("updatedAt" to FieldValue.serverTimestamp()))
            .addOnFailureListener { error ->
                Log.e(TAG, "Could not save the FCM token.", error)
            }
    }

    private const val TAG = "KnowHUBMessaging"
}
