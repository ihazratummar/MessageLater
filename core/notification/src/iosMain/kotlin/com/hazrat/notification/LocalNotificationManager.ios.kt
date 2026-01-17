package com.hazrat.notification

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitSecond
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSCharacterSet
import platform.Foundation.NSDate
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.URLQueryAllowedCharacterSet
import platform.Foundation.create
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIApplication
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationAction
import platform.UserNotifications.UNNotificationActionOptionForeground
import platform.UserNotifications.UNNotificationCategory
import platform.UserNotifications.UNNotificationCategoryOptionNone
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter

/**
 * iOS implementation of LocalNotificationManager using UNUserNotificationCenter.
 *
 * @author hazratummar Created on 16/01/26
 */
@OptIn(ExperimentalForeignApi::class, kotlinx.cinterop.BetaInteropApi::class)
actual class LocalNotificationManager {

    companion object {
        private const val TAG = "NotificationModule"
        private const val WHATSAPP_CATEGORY = "WHATSAPP_REMINDER"
        private const val ACTION_OPEN_WHATSAPP = "OPEN_WHATSAPP"
    }

    private val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

    init {
        registerNotificationCategory()
    }

    /**
     * Register notification category with WhatsApp action button. This enables the "Open WhatsApp"
     * button on notifications.
     */
    private fun registerNotificationCategory() {
        val openWhatsAppAction = UNNotificationAction.actionWithIdentifier(
            identifier = ACTION_OPEN_WHATSAPP,
            title = "Open WhatsApp",
            options = UNNotificationActionOptionForeground
        )

        val category = UNNotificationCategory.categoryWithIdentifier(
            identifier = WHATSAPP_CATEGORY,
            actions = listOf(openWhatsAppAction),
            intentIdentifiers = emptyList<String>(),
            options = UNNotificationCategoryOptionNone
        )

        notificationCenter.setNotificationCategories(setOf(category))
        println("✅ [$TAG] Notification category registered")
    }

    /** Show notification immediately. */
    actual fun showNotification(
        title: String,
        body: String,
        notificationId: String,
        whatsappNumber: String?,
        whatsappMessage: String?
    ) {
        val content = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(body)
            setSound(UNNotificationSound.defaultSound)

            // Set category for action buttons
            if (!whatsappNumber.isNullOrBlank()) {
                setCategoryIdentifier(WHATSAPP_CATEGORY)
                // Store WhatsApp data in userInfo for action handling
                setUserInfo(
                    mapOf(
                        "notificationId" to notificationId,
                        "whatsappNumber" to whatsappNumber,
                        "whatsappMessage" to (whatsappMessage ?: "")
                    )
                )
            }
        }

        // Trigger after 0.1 second (minimum) for immediate display
        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
            timeInterval = 0.1,
            repeats = false
        )

        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = notificationId,
            content = content,
            trigger = trigger
        )

        notificationCenter.addNotificationRequest(request) { error ->
            if (error != null) {
                println("❌ [$TAG] Error showing notification: ${error.localizedDescription}")
            } else {
                println("✅ [$TAG] Notification shown (ID: $notificationId)")
            }
        }
    }

    /** Schedule notification for a future time. */
    actual fun scheduleNotification(
        id: String,
        title: String,
        body: String,
        timestamp: Long,
        whatsappNumber: String?,
        whatsappMessage: String?
    ): Boolean {
        // Validate timestamp is in the future
        val currentTime = (NSDate().timeIntervalSince1970 * 1000).toLong()
        if (timestamp <= currentTime) {
            println("⚠️ [$TAG] Cannot schedule notification in the past (ID: $id)")
            return false
        }

        val content = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(body)
            setSound(UNNotificationSound.defaultSound)

            // Set category for action buttons
            if (!whatsappNumber.isNullOrBlank()) {
                setCategoryIdentifier(WHATSAPP_CATEGORY)
                setUserInfo(
                    mapOf(
                        "notificationId" to id,
                        "whatsappNumber" to whatsappNumber,
                        "whatsappMessage" to (whatsappMessage ?: "")
                    )
                )
            }
        }

        // Convert timestamp (milliseconds) to NSDate
        val date = NSDate.dateWithTimeIntervalSince1970(timestamp / 1000.0)

        // Extract date components for trigger
        val calendar = NSCalendar.currentCalendar
        val components = calendar.components(
                unitFlags = NSCalendarUnitYear or
                        NSCalendarUnitMonth or
                        NSCalendarUnitDay or
                        NSCalendarUnitHour or
                        NSCalendarUnitMinute or
                        NSCalendarUnitSecond,
                fromDate = date
            )

        val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(
            dateComponents = components,
            repeats = false
        )

        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = id,
            content = content,
            trigger = trigger
        )

        notificationCenter.addNotificationRequest(request) { error ->
            if (error != null) {
                println("❌ [$TAG] Error scheduling notification: ${error.localizedDescription}")
            } else {
                println("✅ [$TAG] Notification scheduled (ID: $id)")
            }
        }

        return true
    }

    /** Cancel a pending or delivered notification. */
    actual fun cancelNotification(id: String) {
        // Remove pending notification
        notificationCenter.removePendingNotificationRequestsWithIdentifiers(listOf(id))

        // Remove delivered notification from notification center
        notificationCenter.removeDeliveredNotificationsWithIdentifiers(listOf(id))

        println("✅ [$TAG] Notification canceled (ID: $id)")
    }

    /**
     * Open WhatsApp with a pre-filled message. Called from Swift when user taps the "Open WhatsApp"
     * action button.
     */
    fun openWhatsApp(phoneNumber: String, message: String) {
        val cleanNumber = phoneNumber.replace(Regex("[^0-9]"), "")
        val encodedMessage = encodeURLParameter(message)

        val urlString =
            if (message.isBlank()) {
                "https://wa.me/$cleanNumber"
            } else {
                "https://wa.me/$cleanNumber?text=$encodedMessage"
            }

        val url = NSURL.URLWithString(urlString)
        if (url != null) {
            UIApplication.sharedApplication.openURL(url)
            println("✅ [$TAG] Opening WhatsApp: $urlString")
        } else {
            println("❌ [$TAG] Invalid WhatsApp URL")
        }
    }

    /** URL encode a string for use in query parameters. */
    private fun encodeURLParameter(value: String): String {
        val nsString = NSString.create(string = value)
        return nsString.stringByAddingPercentEncodingWithAllowedCharacters(
            NSCharacterSet.URLQueryAllowedCharacterSet
        )
            ?: value
    }
}
