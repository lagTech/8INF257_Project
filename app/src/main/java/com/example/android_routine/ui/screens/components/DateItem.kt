package com.example.android_routine.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DateItem(name: String, icon: ImageVector, color: Long) {
    Row(
        modifier = Modifier
            .padding(3.dp)
            .size(width = 150.dp, height = 50.dp),
        //.padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon,
            contentDescription = name,
            modifier = Modifier.size(27.dp),
            tint = Color(color)
        )
        Text(
            text = name,
            color = Color(0xB3333333),
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 2.dp)
        )

    }
}