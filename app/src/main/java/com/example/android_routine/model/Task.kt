package com.example.android_routine.model

data class Task(
    val id: Int,
    val title: String,
    val category: String,
    val dueTime: String?,
    val notes: String?,
    val isCompleted: Boolean = false,
    val isToday: Boolean = false
)
