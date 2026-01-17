package com.hazrat.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Composable
expect fun DatePickerDialog(
    selectedDate: LocalDate,
    onDateSelected : (LocalDate) -> Unit,
    onDismiss : () -> Unit,
    modifier: Modifier = Modifier,
    minDate: LocalDate ? = null,
    maxDate: LocalDate ? = null
)
