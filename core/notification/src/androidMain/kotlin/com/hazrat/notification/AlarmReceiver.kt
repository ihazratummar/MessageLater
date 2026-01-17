package com.hazrat.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.koin.core.context.GlobalContext

/** @author hazratummar Created on 13/01/26 */
class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "NotificationModule"
    }

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val koin = GlobalContext.getOrNull()
            if (koin == null) {
                Log.e(TAG, "Koin not initialized, cannot show notification")
                return
            }

            val localNotificationManager = koin.getOrNull<LocalNotificationManager>()
            if (localNotificationManager == null) {
                Log.e(TAG, "LocalNotificationManager not found in Koin")
                return
            }

            val title = intent.getStringExtra("title")
            if (title == null) {
                Log.w(TAG, "Missing title in alarm intent")
                return
            }

            val body = intent.getStringExtra("body")
            if (body == null) {
                Log.w(TAG, "Missing body in alarm intent")
                return
            }

            val id = intent.getStringExtra("id") ?: System.currentTimeMillis().toString()
            val whatsappNumber = intent.getStringExtra("whatsappNumber")
            val whatsappMessage = intent.getStringExtra("whatsappMessage")

            Log.d(TAG, "⏰ Alarm triggered for notification (ID: $id)")

            localNotificationManager.showNotification(
                    title = title,
                    body = body,
                    notificationId = id,
                    whatsappNumber = whatsappNumber,
                    whatsappMessage = whatsappMessage
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to show notification from alarm: ${e.message}", e)
        }
    }
}
