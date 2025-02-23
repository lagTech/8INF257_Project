package com.example.android_routine.ui.screens.alltask

import androidx.lifecycle.ViewModel
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar

class AllTasksViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    data class AllTasksUiState(
        val overdueTasks: List<Task> = emptyList(),
        val allTasks: List<Task> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(AllTasksUiState())
    val uiState: StateFlow<AllTasksUiState> = _uiState.asStateFlow()


    init {
        updateTasks()
    }

    private fun updateTasks() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val allTasks = repository.getAllTasks()

        _uiState.update { currentState ->
            currentState.copy(
                overdueTasks = allTasks.filter { task ->
                    val dueTime = task.dueTime?.let { parseTime(it) }

                    if (dueTime != null && task.isToday && !task.isCompleted) {
                        when {
                            dueTime.hour < currentHour -> true
                            dueTime.hour == currentHour && dueTime.minute < currentMinute -> true
                            else -> false
                        }
                    } else false
                },
                allTasks = allTasks.filter { !it.isCompleted }
            )
        }
    }

    private fun parseTime(timeStr: String): Time {
        val parts = timeStr.split(":")
        val hourMinute = parts[1].split(" ")

        var hour = parts[0].toInt()
        val minute = hourMinute[0].toInt()
        val isPM = hourMinute[1].uppercase() == "PM"

        // Convert to 24-hour format
        if (isPM && hour != 12) hour += 12
        if (!isPM && hour == 12) hour = 0

        return Time(hour, minute)
    }

    private data class Time(val hour: Int, val minute: Int)


    fun deleteTask(taskId: Int) {
        repository.deleteTask(taskId)
        updateTasks()
    }

    fun toggleTaskCompletion(taskId: Int) {
      repository.toggleTaskCompletion(taskId)
        updateTasks()
    }
}