package com.example.android_routine.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.android_routine.model.Task

@Composable
fun TaskItem(task: Task) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            RadioButton(
                selected = task.isCompleted,
                onClick = {},
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(23.dp),
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF2196F3),
                    unselectedColor = Color(0xFF2196F3),
                    disabledSelectedColor = Color(0xFF2196F3).copy(alpha = 0.6f),
                    disabledUnselectedColor = Color(0xFF2196F3).copy(alpha = 0.6f)
                ),
            )

            Text(
                task.title,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xFF333333).copy(alpha = 0.70f)
            )
        }


            task.dueTime?.let { Text(it, color = Color.Gray, fontSize = 14.sp) }

    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskItem(
        task = Task(
            id = 1,
            title = "Complete Android Project",
            dueTime = "2:30 PM",
            isCompleted = false,
            isToday = true,
            notes = "kkikkkk",
            category = "hhhh"
        )
    )
}