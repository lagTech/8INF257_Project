package com.example.android_routine.view.components


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryItem(name: String, icon: ImageVector, color: Long) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 103.dp, height = 88.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF2196F3),
                shape = RoundedCornerShape(10.dp)
            ).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon,
            contentDescription = name,
            modifier = Modifier.size(27.dp),
            tint = Color(color)
        )
        Text(
            text = name,
            color = Color(color),
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal
        )

    }
}

@Preview
@Composable
fun CategoryItemPreview(){
    CategoryItem(
        name = "Personal",
        icon = Icons.Outlined.Person,
        color = 0xFFFF5722
    )
}