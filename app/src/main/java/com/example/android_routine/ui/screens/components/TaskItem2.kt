@file:Suppress("UNREACHABLE_CODE")

package com.example.android_routine.ui.screens.components

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
import com.example.android_routine.data.model.Task

@Composable
fun TaskItem2(
    task : Task,
    onTaskClick: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
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
                checked = true,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Transparent,
                    uncheckedColor = Color.Blue
                ),
                modifier = Modifier
                    .border(
                        border = BorderStroke(2.dp,Color.Blue),
                    )
                )
            Column {
                Text(
                    text= task.title,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333).copy(alpha = 0.70f),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 0.dp)

                )
                Text(
                    text= task.dueTime.toString(),
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 0.dp)

                )
            }

            }
}

fun Checkbox(checked: Boolean, onCheckedChange: Nothing?, modifier: Modifier.Companion, colors: Color) {

}
