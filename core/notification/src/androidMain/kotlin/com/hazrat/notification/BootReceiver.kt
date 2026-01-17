package com.hazrat.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.hazrat.common.domain.model.ReminderState
import com.hazrat.common.domain.repository.ReminderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext

/**
 * Reschedules all pending reminders after device reboot.
 *
 * Android clears all scheduled alarms when the device reboots, so this receiver queries the
 * database for pending reminders and reschedules them.
 *
 * @author hazratummar Created on 14/01/26
 */
class BootReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "NotificationModule"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) {
            return
        }

        Log.d(TAG, "📱 Device boot detected, rescheduling pending reminders...")

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            try {
                rescheduleAllPendingReminders()
            } catch (e: Exception) {
                Log.e(TAG, "Error rescheduling reminders after boot: ${e.message}", e)
            } finally {
                pendingResult.finish()
            }
        }
    }

    private suspend fun rescheduleAllPendingReminders() {
        val koin = GlobalContext.getOrNull()
        if (koin == null) {
            Log.e(TAG, "Koin not initialized, cannot reschedule reminders")
            return
        }

        val repository = koin.getOrNull<ReminderRepository>()
        if (repository == null) {
            Log.e(TAG, "ReminderRepository not found in Koin")
            return
        }

        val notificationManager = koin.getOrNull<LocalNotificationManager>()
        if (notificationManager == null) {
            Log.e(TAG, "LocalNotificationManager not found in Koin")
            return
        }

        try {
            // Get all active reminders (PENDING or SCHEDULED state)
            val activeReminders = repository.getActiveReminder()
            val pendingReminders =
                    activeReminders.filter { reminder ->
                        (reminder.state == ReminderState.PENDING ||
                                reminder.state == ReminderState.SCHEDULED) &&
                                reminder.scheduledAt > System.currentTimeMillis()
                    }

            Log.d(TAG, "Found ${pendingReminders.size} pending reminders to reschedule")

            var successCount = 0
            var failCount = 0

            pendingReminders.forEach { reminder ->
                val scheduled =
                        notificationManager.scheduleNotification(
                                id = reminder.id,
                                title = "Reminder: ${reminder.contactName}",
                                body = reminder.message,
                                timestamp = reminder.scheduledAt,
                                whatsappNumber = reminder.contactNumber,
                                whatsappMessage = reminder.message
                        )

                if (scheduled) {
                    successCount++
                } else {
                    failCount++
                    Log.w(TAG, "Failed to reschedule reminder (ID: ${reminder.id})")
                }
            }

            Log.d(TAG, "✅ Rescheduled $successCount reminders, $failCount failed")
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching reminders: ${e.message}", e)
        }
    }
}
