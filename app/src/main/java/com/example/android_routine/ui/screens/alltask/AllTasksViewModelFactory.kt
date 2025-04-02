package com.example.android_routine.ui.screens.alltask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_routine.data.repository.CategoryRepository
import com.example.android_routine.data.repository.TaskRepository

class AllTasksViewModelFactory(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllTasksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AllTasksViewModel(taskRepository, categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
