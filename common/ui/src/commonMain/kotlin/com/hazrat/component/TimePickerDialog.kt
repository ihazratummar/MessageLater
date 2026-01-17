package com.hazrat.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalTime


/**
 * @author hazratummar
 * Created on 12/01/26
 */

@Composable
expect fun TimePickerDialog(
    selectedTime : LocalTime,
    onTimeSelected : (LocalTime) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    is24HourView: Boolean = true
)