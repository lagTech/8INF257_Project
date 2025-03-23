package com.example.android_routine.ui.screens.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.ui.screens.home.HomeViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddTaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // UI State for Add Task Screen
    data class AddTaskUiState(
        val title: String = "",
        val category: String = "Work",
        val dueTime: String = "",
        val dueDate: String = "",
        val priority: String = "Daily",
        val periodicity: String = "Once",
        val notes: String = "",
        val isError: Boolean = false,
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title, isError = false) }
    }

    fun updateDueTime(dueTime: String) {
        _uiState.update { it.copy(dueTime = dueTime) }
    }

    fun updateDueDate(dueDate: String) {
        _uiState.update { it.copy(dueDate = dueDate) }
    }

    fun updatePeriodicity(periodicity: String) {
        _uiState.update { it.copy(periodicity = periodicity) }
    }

    fun updateNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    // Validate and add a new task
    fun addTask(): Boolean {
        val currentState = _uiState.value
        if (currentState.title.isBlank()) {
            _uiState.update { it.copy(isError = true, errorMessage = "Title cannot be empty") }
            return false
        }
        // Format dueDate correctly
        val formattedDueDate = if (currentState.dueDate.isNotBlank()) {
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val parsedDate = inputFormat.parse(currentState.dueDate)
                inputFormat.format(parsedDate ?: Date()) // Ensures correct format
            } catch (e: Exception) {
                null // If parsing fails, store null
            }
        } else null

        viewModelScope.launch {
            val newTask = Task(
                id = System.currentTimeMillis().toInt(), // Unique ID based on timestamp
                title = currentState.title,
                category = currentState.category,
                dueTime = currentState.dueTime.takeIf { it.isNotBlank() },
                dueDate = formattedDueDate,
                notes = currentState.notes.takeIf { it.isNotBlank() },
                priority = currentState.priority,
                periodicity = currentState.periodicity,
            )

            repository.addTask(newTask)
        }
        return true
    }

}
