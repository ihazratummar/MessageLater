package com.hazrat.create_reminder.ui


/**
 * @author hazratummar
 * Created on 11/01/26
 */

sealed interface CreateReminderScreenEffect {

    /**
     * Message / Feedback
     */

    /** Show a success message (schedule created) */
    data class ShowSuccessMessage(val message: String) : CreateReminderScreenEffect

    /** Show an error message */
    data class ShowError(val message: String) : CreateReminderScreenEffect

    /** Show a toast for Pro feature locked */
    data class ShowProLockedToast(val featureName: String) : CreateReminderScreenEffect


    /**
     * Platform Actions
     */

    /** Request contact permission (if not granted) */
    data object RequestContactPermission : CreateReminderScreenEffect

    /** Request notification permission (for reminders) */
    data object RequestNotificationPermission : CreateReminderScreenEffect


    /**
     * Navigation
     */

    /** Navigate back to the previous screen */
    data object NavigateBack : CreateReminderScreenEffect

    /** Navigate to the Pro/Upgrade screen */
    data object NavigateToPro : CreateReminderScreenEffect



    /**
     * Platform Actions
     */

    /** Trigger success haptic feedback */
    data object HapticSuccess : CreateReminderScreenEffect

    /** Trigger error haptic feedback */
    data object HapticError : CreateReminderScreenEffect

}