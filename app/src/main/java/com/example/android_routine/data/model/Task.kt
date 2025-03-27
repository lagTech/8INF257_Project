package com.example.android_routine.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val categoryId: Int?,
    val dueTime: String?,
    val dueDate: String?,
    val notes: String?,
    val priority: String?,
    val periodicity: String?,
    val isCompleted: Boolean = false
)
