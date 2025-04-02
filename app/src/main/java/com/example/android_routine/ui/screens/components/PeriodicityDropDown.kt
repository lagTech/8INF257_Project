package com.example.android_routine.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.android_routine.ui.screens.addtask.AddTaskViewModel
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class) // Enables Experimental Material API
@Composable
fun PeriodicityDropDown(viewModel: AddTaskViewModel) {
    val periodicityOptions = listOf("Daily", "Just today", "Weekly", "Monthly", "Yearly")
    var expanded by remember { mutableStateOf(false) }
    val selectedPeriodicity by viewModel.uiState.collectAsState()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = selectedPeriodicity.periodicity, // Fix: Access periodicity correctly
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Periodicity") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .padding(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            periodicityOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        viewModel.onEvent(AddTaskViewModel.TaskEvent.UpdatePeriodicity(option)) // Update ViewModel
                        expanded = false
                    }
                )
            }
        }
    }
}
