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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.UIKit.UIColor
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle
import platform.UIKit.systemBackgroundColor

@androidx.compose.runtime.Composable
actual fun DatePickerDialog(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier,
    minDate: LocalDate?,
    maxDate: LocalDate?
) {

    var pickerRef by remember { mutableStateOf<UIDatePicker?>(null) }

    Dialog(
        onDismissRequest = onDismiss
    ){
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ){
            Column(
                modifier = Modifier.padding(AppDimens.spacingLg),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Select Date",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = AppDimens.spacingLg)
                )


                UIKitView(
                    factory = {
                        UIDatePicker().apply {
                            datePickerMode = UIDatePickerMode.UIDatePickerModeDate
                            preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels

                            // Add Solid background color
                            backgroundColor = UIColor.systemBackgroundColor

                            val calendar = NSCalendar.currentCalendar
                            val components = NSDateComponents().apply {
                                year = selectedDate.year.toLong()
                                month = selectedDate.monthNumber.toLong()
                                day = selectedDate.dayOfMonth.toLong()
                            }

                            date = calendar.dateFromComponents(components) ?: NSDate()

                            minDate?.let {
                                val minComponents = NSDateComponents().apply {
                                    year = it.year.toLong()
                                    month = it.monthNumber.toLong()
                                    day = it.dayOfMonth.toLong()
                                }
                                minimumDate = calendar.dateFromComponents(minComponents)
                            }

                            maxDate?.let {
                                val maxComponents = NSDateComponents().apply {
                                    year = it.year.toLong()
                                    month = it.monthNumber.toLong()
                                    day = it.dayOfMonth.toLong()
                                }
                                maximumDate = calendar.dateFromComponents(maxComponents)
                            }
                            pickerRef = this
                        }
                    },
                    modifier = Modifier.height(AppDimens.minCardHeight).fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppDimens.spacingLg),
                    horizontalArrangement = Arrangement.End
                ){
                    TextButton(onClick = onDismiss){
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(AppDimens.spacingSm))
                    TextButton(
                        onClick = {
                            pickerRef?.let { picker ->
                                val calendar = NSCalendar.currentCalendar
                                val components = calendar.components(
                                    NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
                                    picker.date
                                )
                                val date = LocalDate(
                                    year = components.year.toInt(),
                                    monthNumber = components.month.toInt(),
                                    dayOfMonth = components.day.toInt()
                                )
                                onDateSelected(date)
                            }
                        }
                    ){
                        Text("Ok")
                    }
                }
            }
        }
    }

}

