package com.example.android_routine.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrioritySection() {
    var selectedOption by remember { mutableStateOf("Moyenne") }

    val options = listOf("Faible", "Moyenne", "Elevee")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .size(width = 150.dp, height = 50.dp),
        horizontalArrangement = Arrangement.Start, // Aligns items to the left
        verticalAlignment = Alignment.CenterVertically // Aligns items vertically
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .clickable { selectedOption = option } // Click anywhere to select
                    .padding(end = 12.dp), // Spacing between items
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = (selectedOption == option),
                    onClick = { selectedOption = option },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF2196F3),
                        unselectedColor = Color.LightGray
                    )
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 1.dp),
                    color = Color(0xB3333333)
                )
            }
        }
    }
}