package com.hazrat.component


import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import kotlinx.datetime.LocalDate
import androidx.compose.ui.Modifier
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalTime

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
@Composable
actual fun DatePickerDialog(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier,
    minDate: LocalDate?,
    maxDate: LocalDate?
) {

    val context = LocalContext.current

    DisposableEffect(Unit){
        val calendar = Calendar.getInstance().apply {
            set(selectedDate.year, selectedDate.monthNumber - 1, selectedDate.dayOfMonth)
        }

        val dialog = DatePickerDialog(
            context,
            {_, year, month, dayOfMonth ->
                val date = LocalDate(year = year, month = month +1, day = dayOfMonth)
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )

        minDate?.let {
            val minCalendar = Calendar.getInstance().apply {
                set(it.year, it.monthNumber - 1 , it.dayOfMonth)
            }
            dialog.datePicker.minDate = minCalendar.timeInMillis
        }

        maxDate?.let {
            val maxCalendar = Calendar.getInstance().apply {
                set(it.year, it.monthNumber - 1 , it.dayOfMonth)
            }
            dialog.datePicker.maxDate = maxCalendar.timeInMillis
        }

        dialog.setOnDismissListener { onDismiss() }

        dialog.show()
        onDispose {
            dialog.dismiss()
        }
    }

}

