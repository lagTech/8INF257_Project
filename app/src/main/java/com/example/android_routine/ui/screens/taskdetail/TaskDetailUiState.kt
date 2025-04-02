package com.example.android_routine.ui.screens.taskdetail


data class TaskDetailUiState(
    val title: String = "",
    val categoryId: Int? = null,
    val categoryName: String = "Work",
    val dueTime: String = "",
    val dueDate: String = "",
    val priority: String = "Daily",
    val periodicity: String = "Once",
    val notes: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null,

    // Form fields
)