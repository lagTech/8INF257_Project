package com.example.android_routine.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val category: String,
    val dueTime: String?,
    val dueDate: String?,
    val notes: String?,
    val priority: String?,
    val periodicity: String?,
    val isCompleted: Boolean = false
)
