package com.hazrat.create_reminder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazrat.common.domain.model.Reminder
import com.hazrat.common.domain.model.ReminderState
import com.hazrat.common.domain.model.RepeatOption
import com.hazrat.common.domain.repository.ReminderRepository
import com.hazrat.contacts.Contact
import com.hazrat.contacts.ContactManager
import com.hazrat.permissions.PermissionManager
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.contacts.READ_CONTACTS
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


/**
 * @author hazratummar
 * Created on 11/01/26
 */

class CreateReminderScreenViewModel(
    private val permissionManager: PermissionManager,
    private val contactManager: ContactManager,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateReminderStates())
    val state: StateFlow<CreateReminderStates> = _state.asStateFlow()


    private val _effect = MutableSharedFlow<CreateReminderScreenEffect>()
    val effect: SharedFlow<CreateReminderScreenEffect> = _effect.asSharedFlow()

    init {
        loadInitialData()
    }


    private fun loadInitialData() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    currentScheduleCount = 0,
                    scheduleLimit = 5,
                    isPro = true
                )
            }
        }
    }


    /**
     * Event Handle
     */


    fun onEvent(event: CreateReminderEvents) {
        when (event) {

            // Form Inputs
            is CreateReminderEvents.MessageChanges -> handleMessageChange(event.message)
            is CreateReminderEvents.ContactSelected -> handleContact(contact = event.contact)
            CreateReminderEvents.ContactCleared -> handleContactClear()
            is CreateReminderEvents.DateSelected -> handleDateSelected(date = event.date)
            is CreateReminderEvents.TimeSelected -> handleTimeSelected(time = event.time)
            is CreateReminderEvents.RepeatOptionSelected -> handleRepeatOption(option = event.option)

            // Dialogs
            CreateReminderEvents.OpenContactPicker -> openContactPicker()
            CreateReminderEvents.CloseContactPicker -> closeContactPicker()
            CreateReminderEvents.OpenDatePicker -> openDatePicker()
            CreateReminderEvents.ClosedDatePicker -> closeDatePicker()
            CreateReminderEvents.OpenTimePicker -> openTimePicker()
            CreateReminderEvents.CloseTimePicker -> closeTimePicker()
            CreateReminderEvents.DismissLimitDialog -> dismissLimitDialog()

            // Actions
            CreateReminderEvents.SaveSchedule -> saveSchedule()
            CreateReminderEvents.NavigateToPro -> navigateToPro()

            // Validation
            CreateReminderEvents.ValidateForm -> validateForm()
            CreateReminderEvents.ClearErrors -> clearErrors()

            // Systems
            CreateReminderEvents.DismissSuccessMessage -> dismissSuccessMessage()
            is CreateReminderEvents.Error -> handleError(message = event.message)
            CreateReminderEvents.WatchAdForSlot -> TODO()
        }
    }

    private fun handleMessageChange(message: String) {
        _state.update {
            it.copy(
                message = message,
                messageError = if (message.length > 500) "Message too long" else null
            )
        }
    }

    private fun handleContact(contact: Contact) {
        _state.update {
            it.copy(
                contactName = contact.name,
                contactNumber = contact.phoneNumbers[0],
                contactError = null,
                isContactPickerOpen = false
            )
        }
    }

    private fun handleContactClear() {
        _state.update {
            it.copy(
                contactName = "",
                contactNumber = "",
                contactError = null
            )
        }
    }

    private fun handleDateSelected(date: LocalDate) {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        _state.update {
            it.copy(
                selectedDate = date,
                dateError = if (date < today) "Date cannot be in the past" else null,
                isDatePickerOpen = false
            )
        }
    }

    private fun handleTimeSelected(time: LocalTime) {
        _state.update {
            it.copy(
                selectedTime = time,
                timeError = null, isTimePickerOpen = false
            )
        }
    }

    private fun handleRepeatOption(option: RepeatOption) {
        val currentState = _state.value
        // Check if Pro feature
        if (option != RepeatOption.NONE && !currentState.isPro) {
            viewModelScope.launch {
                _effect.emit(CreateReminderScreenEffect.ShowProLockedToast("Recurring Reminders"))
            }
            return
        }
        _state.update { it.copy(repeat = option) }
    }

    /* Dialogs */

    private fun openContactPicker() {
        viewModelScope.launch {
            val isGranted = permissionManager.isGranted(permission = Permission.READ_CONTACTS)
            if (isGranted) {
                loadContact()
                _state.update { it.copy(isContactPickerOpen = true) }

            } else {
                val isGranted = permissionManager.request(permission = Permission.READ_CONTACTS)
                if (isGranted) {
                    loadContact()
                    _state.update { it.copy(isContactPickerOpen = true) }
                } else {
                    _effect.emit(CreateReminderScreenEffect.ShowError("Contact Permission Denied"))
                }
            }
        }

    }

    private fun closeContactPicker() {
        _state.update { it.copy(isContactPickerOpen = false) }
    }

    private fun openDatePicker() {
        _state.update { it.copy(isDatePickerOpen = true) }
    }

    private fun closeDatePicker() {
        _state.update { it.copy(isDatePickerOpen = false) }
    }

    private fun openTimePicker() {
        _state.update { it.copy(isTimePickerOpen = true) }
    }

    private fun closeTimePicker() {
        _state.update { it.copy(isTimePickerOpen = false) }
    }

    private fun dismissLimitDialog() {
        _state.update { it.copy(isLimitDialogOpen = false) }
    }

    private fun dismissSuccessMessage() {
        _state.update { it.copy(showSuccessMessage = false) }
    }

    /* Actions */

    private fun navigateToPro(){
        _state.update { it.copy(isLimitDialogOpen = false) }
        viewModelScope.launch {
            _effect.emit(CreateReminderScreenEffect.NavigateToPro)
        }
    }

    private fun clearErrors(){
        _state.update {
            it.copy(
                contactError = null,
                messageError = null,
                dateError = null,
                timeError = null
            )
        }
    }

    private fun handleError(message: String) {
        viewModelScope.launch {
            _effect.emit(CreateReminderScreenEffect.ShowError(message))
        }
    }


    /*
    Load Contact
     */

    private fun loadContact() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.update { it.copy(isLoadingContacts = true) }
                val contacts = contactManager.getContacts()
                println("Contact loaded: ${contacts.size}")
                contacts.forEach { contact ->
                    println("Contact: ${contact.name} - ${contact.phoneNumbers}")
                }

                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(
                            contacts = contacts,
                            isLoadingContacts = false
                        )
                    }
                }
            } catch (e: Exception) {
                println("Error loading contacts : ${e.message}")
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    _state.update { it.copy(isLoadingContacts = false) }
                    _effect.emit(CreateReminderScreenEffect.ShowError("Failed to load contacts"))
                }
            }
        }
    }

    // Save Schedule

    private fun saveSchedule() {
        viewModelScope.launch {
            val currentState = _state.value
            try {
                val isGranted = permissionManager.isGranted(permission = Permission.REMOTE_NOTIFICATION)
                if (isGranted){
                    saveToDbAndSchedule()
                }else {
                    val isGranted = permissionManager.request(permission = Permission.REMOTE_NOTIFICATION)
                    if (isGranted) {
                        saveToDbAndSchedule()
                    }else {
                        _effect.emit(CreateReminderScreenEffect.ShowError("Notification Permission Denied"))
                    }
                }
            }catch (e: Exception){
                _state.update {
                    it.copy(isLoading = false)
                }
                _effect.emit(CreateReminderScreenEffect.ShowError(e.message ?:"Failed to save schedule"))
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun saveToDbAndSchedule(){
        if (!validateForm()){
            _effect.emit(CreateReminderScreenEffect.HapticError)
            return
        }

        _state.update { it.copy(isLoading = true) }

        val now = Clock.System.now().epochSeconds

        try {
            reminderRepository.insert(
                reminder = Reminder(
                    id = Uuid.random().toString(),
                    contactName = _state.value.contactName,
                    contactNumber = _state.value.contactNumber,
                    message = _state.value.message,
                    scheduledAt = _state.value.toScheduledAt() ?: now,
                    triggeredAt = now,
                    repeatRule = _state.value.repeat,
                    state = ReminderState.SCHEDULED,
                    retryCount = 0,
                    createdAt = now,
                    updatedAt = now,
                )
            )
            _state.update { it.copy(isLoading = false) }
            _effect.emit(CreateReminderScreenEffect.HapticSuccess)
            _effect.emit(CreateReminderScreenEffect.ShowSuccessMessage("Reminder scheduled!"))

            delay(1000)
            _effect.emit(CreateReminderScreenEffect.NavigateBack)
        }catch (e: Exception){
            _state.update { it.copy(isLoading = false) }
            _effect.emit(CreateReminderScreenEffect.ShowError(e.message ?: "Failed to save"))
        }
    }

    private fun validateForm() : Boolean {
        val currentState = _state.value
        var isValid = true

        val contactError =
            when {
                currentState.contactName.isBlank() -> {
                    isValid = false
                    "Please select a contact"
                }
                else -> null
            }
        val messageError = when {
            currentState.message.isBlank() -> {
                isValid = false
                "Please enter a message"
            }
            else -> null
        }

        val dateError = when {
            currentState.selectedDate == null -> {
                isValid = false
                "Please select a date"
            }

            else -> null
        }
        val timeError =
            when {
                currentState.selectedTime == null -> {
                    isValid = false
                    "Please select a time"
                }
                else -> null
            }

        _state.update {
            it.copy(
                contactError = contactError,
                messageError = messageError,
                dateError = dateError,
                timeError = timeError
            )
        }
        return isValid
    }




}