package com.hazrat.create_reminder.ui

import com.hazrat.common.domain.model.RepeatOption
import com.hazrat.contacts.Contact
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime


/**
 * @author hazratummar
 * Created on 11/01/26
 */

sealed interface CreateReminderEvents {

    /**
     *  FORM INPUT EVENTS
     */

    /** User typed in the message Fields */
    data class MessageChanges(val message: String) : CreateReminderEvents

    /** User selected a contact from the contact picker */
    data class ContactSelected(val contact : Contact) : CreateReminderEvents

    /** User clear the selected contact*/
    data object ContactCleared : CreateReminderEvents

    /** User selected a date from the date picker */
    data class DateSelected(val date: LocalDate) : CreateReminderEvents

    /** User selected a time from the date picker */
    data class TimeSelected(val time: LocalTime) : CreateReminderEvents

    /** User tapped on a repeat option */
    data class RepeatOptionSelected(val option: RepeatOption) : CreateReminderEvents

    /**
     *  Dialogs / Picker Events
     */


    /** User tapped on contact field to open contact picker */
    data object OpenContactPicker : CreateReminderEvents


    /** User closed the contact picker */
    data object CloseContactPicker : CreateReminderEvents

    /** User tapped on date field to open date picker */
    data object OpenDatePicker : CreateReminderEvents

    /** User closed the date picker */
    data object ClosedDatePicker : CreateReminderEvents

    /** User tapped on time field to open picker */
    data object OpenTimePicker : CreateReminderEvents

    /** User closed the time picker */
    data object CloseTimePicker : CreateReminderEvents

    /** User dismissed the limit reached dialog */
    data object DismissLimitDialog : CreateReminderEvents

    /**
     *  Action Events
     */

    /** User tapped the Save/Create button */
    data object SaveSchedule : CreateReminderEvents

    /** User tapped "Watch Ad" to get extra slot */
    data object WatchAdForSlot : CreateReminderEvents

    /** User tapped "Go Pro" from limit dialog */
    data object NavigateToPro : CreateReminderEvents



    /**
     *  Validation Events
     */

    /** Validate form before submission */
    data object ValidateForm : CreateReminderEvents

    /** Clear all validation errors */
    data object ClearErrors : CreateReminderEvents

    /**
     *  System Events
     */

    /** Error occurred (generic) */
    data class Error(val message: String) : CreateReminderEvents

    /** Success message was shown, now dismiss it */
    data object DismissSuccessMessage : CreateReminderEvents
}
