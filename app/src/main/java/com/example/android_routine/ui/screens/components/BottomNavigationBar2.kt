package com.example.android_routine.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNav2() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Bottom Navigation Bar
        NavigationBar(
            modifier = Modifier
                .width(370.dp)
                .height(59.dp)
                .background(
                    color = Color.White.copy(alpha = 0.07f),
                    shape = RoundedCornerShape(30.dp)
                )
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(30.dp)
                ),
            containerColor = Color.White
        ) {
            NavigationBarItem(
                icon = { Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (true) Color(0xFF2196F3) else Color.Gray

                ) },
                selected = true,
                onClick = { }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Calendar",
                        tint = if (false) Color(0xFF2196F3) else Color.Gray
                    ) },
                selected = false,
                onClick = { }
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Notes",
                        tint = if (false) Color(0xFF2196F3) else Color.Gray
                    ) },
                selected = false,
                onClick = { }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = if (false) Color(0xFF2196F3) else Color.Gray
                    ) },
                selected = false,
                onClick = { }
            )
        }


    }
}