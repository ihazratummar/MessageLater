package com.hazrat.component

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.datetime.LocalTime

@Composable
actual fun TimePickerDialog(
    selectedTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier,
    is24HourView: Boolean
) {
    val context = LocalContext.current

    DisposableEffect(Unit){
        val dialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val time = LocalTime(hour = hourOfDay, minute = minute)
                onTimeSelected(time)
            },
            selectedTime.hour,
            selectedTime.minute,
            is24HourView
        )

        dialog.setOnDismissListener { onDismiss() }
        dialog.show()

        onDispose {
            dialog.dismiss()
        }
    }


}