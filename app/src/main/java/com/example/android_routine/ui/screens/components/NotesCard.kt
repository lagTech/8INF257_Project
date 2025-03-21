package com.example.android_routine.ui.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotesCard(notes: String, onNotesChange: (String) -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color(0x142196F3)) // Apply blue color

    ) {
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            if (notes.isEmpty()) {
                Text("Enter your notes here...", color = Color.Gray)
            }
            BasicTextField(
                value = notes,
                onValueChange = onNotesChange,
                modifier = Modifier.fillMaxSize(),
                textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 16.sp)
            )
        }
    }
}
