package com.hazrat.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri

actual class LocalNotificationManager(private val context: Context) {

    private val notificationManager = NotificationManagerCompat.from(context)
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    companion object {
        private const val TAG = "NotificationModule"
        private const val CHANNEL_ID = "message_later_channel"
        private const val CHANNEL_NAME = "Message Reminders"

        // Action Identifier
        const val ACTION_OPEN_WHATSAPP = "ACTION_OPEN_WHATSAPP"
        const val ACTION_DISMISS = "ACTION_DISMISS"
        const val ACTION_TAP = "ACTION_TAP"

        // Extra keys
        const val EXTRA_NOTIFICATION_ID = "NOTIFICATION_ID"
        const val EXTRA_PHONE_NUMBER = "PHONE_NUMBER"
        const val EXTRA_MESSAGE = "MESSAGE"
    }

    init {
        createNotificationChannel()
    }

    /**
     * Show notification with Whatsapp action
     * @param whatsappNumber phone number (with country code, e.g., "+919876543210")
     * @param whatsappMessage message to pre-fill
     */
    actual fun showNotification(
        title: String,
        body: String,
        notificationId: String,
        whatsappNumber: String?,
        whatsappMessage: String?
    ) {
        try {
            val builder =
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setVibrate(longArrayOf(0, 250, 250, 250))
                    .setSound(
                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    )

            // Content Intent (when notification body is tapped)
            val tapIntent = Intent(context, NotificationActionReceiver::class.java).apply {
                action = ACTION_TAP
                putExtra(EXTRA_NOTIFICATION_ID, notificationId)
            }
            val tapPendingIntent = PendingIntent.getBroadcast(
                context,
                (notificationId + "_tap").hashCode(),
                tapIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            builder.setContentIntent(tapPendingIntent)

            // Delete Intent (when notification is dismissed/swiped away)
            val dismissIntent = Intent(context, NotificationActionReceiver::class.java).apply {
                    action = ACTION_DISMISS
                    putExtra(EXTRA_NOTIFICATION_ID, notificationId)
                }
            val dismissPendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    (notificationId + "_dismiss").hashCode(),
                    dismissIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            builder.setDeleteIntent(dismissPendingIntent)

            // Add Whatsapp action button if number is provided and WhatsApp is installed
            if (!whatsappNumber.isNullOrBlank() && isWhatsAppInstalled()) {
                val clean = whatsappNumber.replace(Regex("[^0-9]"), "")
                val encoded = whatsappMessage?.let { Uri.encode(it) }

                val url =
                    if (encoded.isNullOrBlank()) {
                        "https://wa.me/$clean"
                    } else {
                        "https://wa.me/$clean?text=$encoded"
                    }

                val proxyIntent =
                    Intent(context, WhatsAppProxyActivity::class.java).apply {
                        putExtra("id", notificationId)
                        putExtra("url", url)
                    }

                val waPendingIntent =
                    PendingIntent.getActivity(
                        context,
                        (notificationId + "_wa").hashCode(),
                        proxyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                builder.addAction(android.R.drawable.ic_menu_send, "Open WhatsApp", waPendingIntent)
            }

            val notification = builder.build()
            notificationManager.notify(notificationId.hashCode(), notification)

            Log.d(TAG, "✅ Notification shown successfully (ID: $notificationId)")
        } catch (e: SecurityException) {
            Log.e(TAG, "SecurityException when showing notification: ${e.message}", e)
        } catch (e: Exception) {
            Log.e(TAG, "Exception when showing notification: ${e.message}", e)
        }
    }

    actual fun scheduleNotification(
        id: String,
        title: String,
        body: String,
        timestamp: Long,
        whatsappNumber: String?,
        whatsappMessage: String?
    ): Boolean {
        // Validate timestamp is in the future
        if (timestamp <= System.currentTimeMillis()) {
            Log.w(TAG, "Cannot schedule notification in the past (ID: $id)")
            return false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Log.w(TAG, "Exact alarm scheduling not allowed")
                return false
            }
        }

        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra("title", title)
                putExtra("body", body)
                putExtra("id", id)
                putExtra("whatsappNumber", whatsappNumber)
                putExtra("whatsappMessage", whatsappMessage)
                data = "custom://${id}".toUri()
            }
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        return try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timestamp,
                pendingIntent
            )
            Log.d(TAG, "✅ Notification scheduled successfully (ID: $id)")
            true
        } catch (e: SecurityException) {
            Log.e(TAG, "SecurityException when scheduling notification: ${e.message}", e)
            false
        }
    }

    actual fun cancelNotification(id: String) {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply { data = "custom://$id".toUri() }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        alarmManager.cancel(pendingIntent)
        Log.d(TAG, "✅ Alarm canceled successfully (ID: $id)")

        notificationManager.cancel(id.hashCode())
        Log.d(TAG, "✅ Notification canceled successfully (ID: $id)")
    }

    /** Check if WhatsApp is installed on the device */
    private fun isWhatsAppInstalled(): Boolean {
        return try {
            context.packageManager.getPackageInfo("com.whatsapp", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            // Try WhatsApp Business
            try {
                context.packageManager.getPackageInfo("com.whatsapp.w4b", 0)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                Log.w(TAG, "WhatsApp is not installed")
                false
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
                    .apply {
                        description = "Scheduled message reminders"
                        enableVibration(true)
                        enableLights(true)
                        vibrationPattern = longArrayOf(0, 250, 250, 250)
                    }

            val systemManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            systemManager.createNotificationChannel(channel)
        }
    }
}
