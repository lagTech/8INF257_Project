package com.example.android_routine.ui.screens.alltask

import com.example.android_routine.data.viewmodelobject.TaskVM

data class AllTasksUiState(
    val allTasks: List<TaskVM> = emptyList(),
    val overdueTasks: List<TaskVM> = emptyList(),
    val isLoading: Boolean = false
)
