package com.example.android_routine.ui.screens.addtask

data class AddTaskUiState(
    val title: String = "",
    val categoryId: Int? = null,
    val categoryName: String = "Select Category",
    val dueTime: String = "",
    val dueDate: String = "",
    val priority: String = "Daily",
    val periodicity: String = "Once",
    val notes: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null
)
