package com.example.android_routine.ui.screens.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_routine.data.repository.CategoryRepository
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.data.viewmodelobject.TaskVM
import com.example.android_routine.ui.screens.detail.TaskDetailViewModel

class TaskDetailViewModelFactory(
    private val taskId: Int,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskDetailViewModel(taskId, taskRepository, categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}