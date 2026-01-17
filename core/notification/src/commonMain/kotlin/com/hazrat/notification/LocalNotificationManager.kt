package com.hazrat.notification


/**
 * @author hazratummar
 * Created on 13/01/26
 */

expect class LocalNotificationManager {

    fun showNotification(title: String, body: String, notificationId: String, whatsappNumber: String? , whatsappMessage: String?)

    fun scheduleNotification(id: String, title: String, body: String, timestamp: Long, whatsappNumber: String?, whatsappMessage: String?) : Boolean

    fun cancelNotification(id: String)

}