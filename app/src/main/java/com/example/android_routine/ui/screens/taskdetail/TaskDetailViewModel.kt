package com.example.android_routine.ui.screens.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.ui.screens.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskDetailViewModel(
    private val repository: TaskRepository

) : ViewModel() {
    data class TaskDetailUiState(
        val task: Task? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
        // Form fields
        val title: String = "",
        val category: String = "",
        val dueTime: String = "",
        val dueDate: String = "",
        val notes: String = ""
    )

    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    fun loadTask(taskId: Int) {
        val task = repository.getTask(taskId) // Get task from HomeViewModel
        task?.let {
            _uiState.update { currentState ->
                currentState.copy(
                    task = it,
                    title = it.title,
                    category = it.category,
                    dueTime = it.dueTime ?: "",
                    dueDate = it.dueDate ?: "",
                    notes = it.notes ?: ""
                )
            }
        }
    }

    fun saveTask(): Boolean {
        val currentState = _uiState.value
        if (currentState.title.isBlank()) {
            _uiState.update { it.copy(error = "Title cannot be empty") }
            return false
        }

        // Get the current task
        val currentTask = currentState.task
        if (currentTask != null) {
            val updatedTask = currentTask.copy(
                title = currentState.title,
                category = currentState.category,
                dueTime = currentState.dueTime.takeIf { it.isNotBlank() },
                dueDate = currentState.dueDate.takeIf { it.isNotBlank() },
                notes = currentState.notes.takeIf { it.isNotBlank() }
            )

            // Update the task in repository
            repository.updateTask(updatedTask)
            return true
        }
        return false
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateCategory(category: String) {
        _uiState.update { it.copy(category = category) }
    }

    fun updateDueTime(dueTime: String) {
        _uiState.update { it.copy(dueTime = dueTime) }
    }

    fun updateDueDate(dueDate: String) {
        _uiState.update { it.copy(dueDate = dueDate) }
    }

    fun updateNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }


    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}