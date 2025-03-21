package com.example.android_routine.ui.screens.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import java.text.SimpleDateFormat


@Composable
fun DateTimePickers(context: Context, onDateSelected: (String) -> Unit, onTimeSelected: (String) -> Unit) {
    var selectedDate by remember { mutableStateOf("Select Due Date") }
    var selectedTime by remember { mutableStateOf("Select Time") }

    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Correct format


    // Date Picker Dialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = dateFormat.format(calendar.time)
            onDateSelected(selectedDate) // Send selected date to ViewModel
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Time Picker Dialog
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            onTimeSelected(selectedTime) // Send selected time to ViewModel
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() } // Open Date Picker
                .padding(12.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "Calendar", tint = Color(0xFFFFC107))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = selectedDate, color = Color(0xFF2196F3), fontSize = 16.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() } // Open Time Picker
                .padding(12.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "Clock", tint = Color(0xFFFF5722))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = selectedTime, color = Color(0xFF2196F3), fontSize = 16.sp)
        }
    }
}
