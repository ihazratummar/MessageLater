package com.hazrat.create_reminder.ui

import com.hazrat.common.domain.model.RepeatOption
import com.hazrat.contacts.Contact
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

data class CreateReminderStates(
    // Form Fields
    val contactName: String = "",
    val contactNumber: String = "",
    val message: String = "",
    val selectedDate: LocalDate? = null,
    val selectedTime: LocalTime? = null,
    val repeat: RepeatOption = RepeatOption.NONE,

    // UI state
    val isContactPickerOpen: Boolean = false,
    val isDatePickerOpen: Boolean = false,
    val isTimePickerOpen: Boolean = false,
    val isLimitDialogOpen: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingContacts: Boolean = false,

    // Validation
    val contactError: String? = null,
    val messageError: String? = null,
    val dateError: String? = null,
    val timeError: String? = null,

    // Pro/Limits
    val currentScheduleCount: Int = 0,
    val scheduleLimit: Int = 5,
    val isPro: Boolean = false,

    // List
    val contacts : List<Contact>? = emptyList(),

    // Navigation
    val showSuccessMessage : Boolean = false
) {
    // Computed properties
    val canSchedule: Boolean
        get() = currentScheduleCount < scheduleLimit || isPro

    val remainingSlots: Int
        get() =
            if (isPro) Int.MAX_VALUE
            else (scheduleLimit - currentScheduleCount).coerceAtLeast(0)

    val isFormValid: Boolean
        get() = contactName.isNotBlank() &&
                message.isNotBlank() &&
                selectedDate != null &&
                selectedTime != null &&
                contactError == null &&
                messageError == null &&
                dateError == null &&
                timeError == null

    val formattedDate : String
        get() = selectedDate?.toString() ?:""

    val formattedTime : String
        get() = selectedTime?.toString() ?:""

    val isRepeatedLocked : Boolean
        get() = !isPro && repeat != RepeatOption.NONE


    fun toScheduledAt(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Long? {
        val date = selectedDate ?: return null
        val time = selectedTime ?: return null

        val localDateTime = LocalDateTime(
            year = date.year,
            monthNumber = date.monthNumber,
            dayOfMonth = date.dayOfMonth,
            hour = time.hour,
            minute = time.minute,
            second = time.second,
            nanosecond = 0
        )

        return localDateTime
            .toInstant(timeZone)
            .toEpochMilliseconds()
    }

}