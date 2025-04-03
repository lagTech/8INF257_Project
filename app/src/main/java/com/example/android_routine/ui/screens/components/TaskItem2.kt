@file:Suppress("UNREACHABLE_CODE")

package com.example.android_routine.ui.screens.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.android_routine.data.viewmodelobject.TaskVM
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TaskItem2(
    task: TaskVM,
    onTaskClick: () -> Unit,
    onDelete: () -> Unit,
    onItemClick: () -> Unit
) {

    fun isOverdue(dueDate: String?, dueTime: String?): Boolean {
        if (dueDate.isNullOrEmpty() || dueTime.isNullOrEmpty()) return false

        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())  // Expected format: "2025-02-23"
        val timeFormat =
            SimpleDateFormat("hh:mm a", Locale.getDefault())     // Expected format: "02:30 PM"

        return try {
            val currentDate = Calendar.getInstance()
            val taskDate = Calendar.getInstance().apply {
                time = dateFormat.parse(dueDate) ?: return false
            }

            // If the due date is before today, the task is overdue
            if (taskDate.before(currentDate)) return true

            // If the due date is today, check the time
            if (taskDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                taskDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)
            ) {
                val currentTime = timeFormat.parse(timeFormat.format(currentDate.time))!!
                val taskTime = timeFormat.parse(dueTime) ?: return false
                return taskTime.before(currentTime)
            }

            false
        } catch (e: Exception) {
            false
        }
    }


    val timeColor = if (isOverdue(task.dueDate, task.dueTime)) Color.Red else Color.Black
    Row(
        modifier = Modifier
            .clickable { onItemClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onTaskClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Blue,
                uncheckedColor = Color.Gray
            ),
            modifier = Modifier
                .border(
                    border = BorderStroke(2.dp, Color.Blue),
                )
        )
        Column {
            Text(
                text = task.title,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333).copy(alpha = 0.70f),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 0.dp)

            )
            Text(
                text = "${task.dueDate ?: "No Date"} ${task.dueTime ?: "No Time"}",
                fontSize = 12.sp,
                color = timeColor,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 0.dp)

            )
        }

    }
}



