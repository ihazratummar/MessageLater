package com.hazrat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import androidx.compose.ui.window.Dialog
import com.hazrat.ui.AppDimens
import kotlinx.datetime.LocalTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.UIKit.UIColor
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle
import platform.UIKit.systemBackgroundColor

@Composable
actual fun TimePickerDialog(
    selectedTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier,
    is24HourView: Boolean
) {

    var pickerRef by remember { mutableStateOf<UIDatePicker?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(AppDimens.spacingLg),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Time",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = AppDimens.spacingLg)
                )

                UIKitView(
                    factory = {
                        UIDatePicker().apply {
                            datePickerMode = UIDatePickerMode.UIDatePickerModeTime
                            preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels

                            backgroundColor = UIColor.systemBackgroundColor

                            // Set initial time
                            val calendar = NSCalendar.currentCalendar
                            val today = NSDate()
                            val dateComponent = calendar.components(
                                NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
                                today
                            )

                            dateComponent.hour = selectedTime.hour.toLong()
                            dateComponent.minute = selectedTime.minute.toLong()

                            date = calendar.dateFromComponents(dateComponent) ?: NSDate()

                            pickerRef = this
                        }
                    },
                    modifier = Modifier.height(AppDimens.minCardHeight).fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spacingLg),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(AppDimens.spacingSm))

                    TextButton(
                        onClick = {
                            pickerRef?.let { picker ->
                                val calendar = NSCalendar.currentCalendar
                                val components = calendar.components(
                                    NSCalendarUnitHour or NSCalendarUnitMinute,
                                    picker.date
                                )

                                val time = LocalTime(
                                    hour = components.hour.toInt(),
                                    minute = components.minute.toInt()
                                )
                                onTimeSelected(time)
                            }
                        }
                    ) {
                        Text("Ok")
                    }
                }
            }
        }
    }

}