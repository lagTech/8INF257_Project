package com.example.android_routine.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.android_routine.data.model.Category

@Composable
fun CategoryDropDownMenu(
    selectedCategoryName: String,
    onCategorySelected: (Int, String) -> Unit,
    categories: List<Category>) {

    var expanded by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxWidth() // Ensure Box takes full width like TextField
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth() // Same width as TextField
                .border(
                    width = 1.dp,
                    color = Color(0xFF2196F3),
                    shape = RoundedCornerShape(10.dp)
                ),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0x142196F3),
                contentColor = Color(0xB3333333)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Ensures alignment
            ) {
                Text(selectedCategoryName, fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Arrow",
                    modifier = Modifier.size(27.dp),
                    tint = Color(0xFF333333)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth() // Ensure the menu takes full width
                .background(Color.White)
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name, color = Color(0xB3333333)) },
                    onClick = {
                        expanded = false
                        onCategorySelected(category.id ?: -1, category.name)
                    }
                )
            }
        }
    }
}
