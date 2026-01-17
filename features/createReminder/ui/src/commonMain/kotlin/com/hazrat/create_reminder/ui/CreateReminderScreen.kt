package com.hazrat.create_reminder.ui

import AppTextStyles
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hazrat.component.DatePickerDialog
import com.hazrat.component.TimePickerDialog
import com.hazrat.create_reminder.ui.component.BottomBar
import com.hazrat.create_reminder.ui.component.ContactInputField
import com.hazrat.create_reminder.ui.component.ContactPickerDialog
import com.hazrat.create_reminder.ui.component.DateInputField
import com.hazrat.create_reminder.ui.component.MessageInputField
import com.hazrat.create_reminder.ui.component.RepeatOptionsSection
import com.hazrat.create_reminder.ui.component.TImeInputField
import com.hazrat.ui.AppDimens
import com.hazrat.ui.MessageLaterTheme.extendedColors
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock


/**
 * @author hazratummar
 * Created on 11/01/26
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReminderScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onProClick: () -> Unit,
    event: (CreateReminderEvents) -> Unit,
    state: CreateReminderStates,
    effect: SharedFlow<CreateReminderScreenEffect>
) {

    // Add SnackBar

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit){
        effect.collectLatest { effect ->
            when(effect) {
                CreateReminderScreenEffect.NavigateBack -> {onBackClick()}
                CreateReminderScreenEffect.NavigateToPro -> {onProClick()}

                is CreateReminderScreenEffect.ShowSuccessMessage -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true,
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Short
                    )
                }
                is CreateReminderScreenEffect.ShowError ->  {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true,
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Short
                    )
                }
                is CreateReminderScreenEffect.ShowProLockedToast -> {
                    snackbarHostState.showSnackbar(
                        "${effect.featureName} available in Pro"
                    )
                }
                else -> {}
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "New Reminder",
                        style = AppTextStyles.screenTitle
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back to Dashboard Button",
                            modifier = Modifier.size(AppDimens.iconSizeSmall),
                            tint = extendedColors.textMuted
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(
                state = state,
                onSaveCLick = {
                    event(CreateReminderEvents.SaveSchedule)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues).fillMaxSize()
                .padding(horizontal = AppDimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(AppDimens.listItemPadding)
        ) {
            item {
                ContactInputField(
                    contactName = state.contactName,
                    contactPhoneNumber = state.contactNumber,
                    onClick = { event(CreateReminderEvents.OpenContactPicker) },
                    onClear = { event(CreateReminderEvents.ContactCleared) },
                    error = state.contactError
                )
            }
            item {
                MessageInputField(
                    message = state.message,
                    error = state.messageError,
                    onMessageChange = {
                        event(CreateReminderEvents.MessageChanges(it))
                    }
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.spacingMd)
                ){
                    DateInputField(
                        date = state.formattedDate,
                        error = state.dateError,
                        onClick = {
                            event(CreateReminderEvents.OpenDatePicker)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    TImeInputField(
                        time = state.formattedTime,
                        error = state.timeError,
                        onClick = {
                            event(CreateReminderEvents.OpenTimePicker)
                        },
                        modifier = Modifier.weight(1f)
                    )


                }
            }

            item {
                RepeatOptionsSection(
                    selectedOption = state.repeat,
                    isPro = state.isPro,
                    onOptionClick = {
                        event(CreateReminderEvents.RepeatOptionSelected(it))
                    }
                )
            }

        }

        if (state.isDatePickerOpen){
            val today = Clock.System.now().toLocalDateTime(timeZone = TimeZone.currentSystemDefault()).date

            DatePickerDialog(
                selectedDate = state.selectedDate ?: today,
                onDateSelected = {
                    event(CreateReminderEvents.DateSelected(date = it))
                },
                onDismiss = {
                    event(CreateReminderEvents.ClosedDatePicker)
                },
                minDate = today
            )
        }

        if (state.isTimePickerOpen){
            val today = Clock.System.now().toLocalDateTime(timeZone = TimeZone.currentSystemDefault()).time
            TimePickerDialog(
                selectedTime = state.selectedTime ?: today,
                onTimeSelected = {
                    event(CreateReminderEvents.TimeSelected(it))
                },
                onDismiss = {
                    event(CreateReminderEvents.CloseTimePicker)
                },
                is24HourView = true
            )
        }

        if (state.isContactPickerOpen){
            state.contacts?.let {
                ContactPickerDialog(
                    contacts = it,
                    isLoading = state.isLoadingContacts,
                    onContactSelected = { contacts ->
                        event(CreateReminderEvents.ContactSelected(contact = contacts))
                    },
                    onDismiss = {
                        event(CreateReminderEvents.CloseContactPicker)
                    }
                )
            }
        }
    }
}