package com.hazrat.states

data class AppEnvironmentState(
    val isUserPro: Boolean = false,
    val totalRemindersCreated: Int = 0,
    val hasContactPermission: Boolean = false,
    val hasNotificationPermission: Boolean = false
)
