package com.hazrat.notification

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import com.hazrat.common.domain.model.ReminderState
import com.hazrat.common.domain.repository.ReminderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext

/**
 * Transparent activity that handles WhatsApp deep link from notification action. Updates the
 * reminder state and opens WhatsApp.
 *
 * @author hazratummar Created on 13/01/26
 */
class WhatsAppProxyActivity : Activity() {

    companion object {
        private const val TAG = "NotificationModule"
    }

    // Activity scope that gets cancelled when activity is destroyed
    private val activityScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("id")
        val url = intent.getStringExtra("url")

        Log.d(TAG, "WhatsAppProxyActivity launched (ID: $id, URL: $url)")

        // Update database in background and dismiss notification
        if (id != null) {
            // Dismiss the notification immediately
            NotificationManagerCompat.from(this).cancel(id.hashCode())
            Log.d(TAG, "✅ Notification dismissed (ID: $id)")

            activityScope.launch { updateReminderState(id) }
        }

        // Open WhatsApp
        if (url != null) {
            try {
                val whatsappIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                // Check if any app can handle this intent
                if (whatsappIntent.resolveActivity(packageManager) != null) {
                    startActivity(whatsappIntent)
                } else {
                    Log.w(TAG, "No app can handle WhatsApp URL")
                    Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error opening WhatsApp: ${e.message}", e)
                Toast.makeText(this, "Failed to open WhatsApp", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.w(TAG, "No URL provided to WhatsAppProxyActivity")
        }

        finish()
    }

    private suspend fun updateReminderState(id: String) {
        try {
            val koin = GlobalContext.getOrNull()
            if (koin == null) {
                Log.e(TAG, "Koin not initialized")
                return
            }

            val repository = koin.getOrNull<ReminderRepository>()
            if (repository == null) {
                Log.e(TAG, "ReminderRepository not found in Koin")
                return
            }

            repository.updateReminderState(
                id = id,
                state = ReminderState.SENT,
                sentAt = System.currentTimeMillis(),
            )
            Log.d(TAG, "✅ Updated reminder state to SENT (ID: $id)")
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error updating reminder state: ${e.message}", e)
        }
    }
}
