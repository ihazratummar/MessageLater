package com.hazrat.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.hazrat.common.domain.model.ReminderState
import com.hazrat.common.domain.repository.ReminderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext

/** @author hazratummar Created on 13/01/26 */
class NotificationActionReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "NotificationModule"
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getStringExtra(LocalNotificationManager.EXTRA_NOTIFICATION_ID)
        if (notificationId == null) {
            Log.w(TAG, "Received notification action without notification ID")
            return
        }

        // Use goAsync() to safely perform async work in BroadcastReceiver
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            try {
                when (intent.action) {
                    LocalNotificationManager.ACTION_DISMISS -> {
                        saveToDatabase(
                            notificationId = notificationId,
                            state = ReminderState.CANCELLED
                        )
                    }

                    LocalNotificationManager.ACTION_TAP -> {
                        saveToDatabase(notificationId = notificationId, state = ReminderState.PENDING)
                    }

                    else -> {
                        Log.w(TAG, "Unknown action received: ${intent.action}")
                    }
                }

                // Dismiss the notification after action
                NotificationManagerCompat.from(context).cancel(notificationId.hashCode())
            } catch (e: Exception) {
                Log.e(TAG, "Error handling notification action: ${e.message}", e)
            } finally {
                // Always finish the pending result to avoid ANR
                pendingResult.finish()
            }
        }
    }

    private suspend fun saveToDatabase(notificationId: String, state: ReminderState) {
        try {
            val koin = GlobalContext.getOrNull()
            if (koin == null) {
                Log.e(TAG, "Koin not initialized, cannot save to database")
                return
            }

            val reminderRepository = koin.getOrNull<ReminderRepository>()
            if (reminderRepository == null) {
                Log.e(TAG, "ReminderRepository not found in Koin")
                return
            }

            reminderRepository.updateReminderState(id = notificationId, state = state)
            Log.d(TAG, "✅ Updated reminder state to $state (ID: $notificationId)")
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error saving to database: ${e.message}", e)
        }
    }
}
