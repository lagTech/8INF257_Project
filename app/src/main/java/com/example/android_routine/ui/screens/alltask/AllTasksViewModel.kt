package com.example.android_routine.ui.screens.alltask

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.data.source.TasksDao
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class AllTasksViewModel(
    val dao: TasksDao
) : ViewModel() {
    private val _tasks: MutableState<List<TaskVM>> =
        var tasks: State<List<TaskVM>> = _tasks
        var job: Job? = null

    init {
        loadTasks()
    }

    private fun loadTasks() {
        job?.cancel()

        job = dao.getTasks().onEach { tasks ->
            _tasks.value = tasks.map{
                TaskVM.fromEntity(it)
            }
        }.launchIn(viewModelScope)
    }

    private fun updateTasks(allTasks: List<Task>) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        _uiState.update { currentState ->
            currentState.copy(
                overdueTasks = allTasks.filter { task ->
                    val dueTime = task.dueTime?.let { parseTime(it) }

                    dueTime?.let {
                        task.isToday && !task.isCompleted && (
                                it.hour < currentHour || (it.hour == currentHour && it.minute < currentMinute)
                                )
                    } ?: false
                },
                allTasks = allTasks.filter { !it.isCompleted }
            )
        }
    }


    private fun parseTime(timeStr: String): Time? {
        try {
            val parts = timeStr.split(":")
            if (parts.size < 2) return null // Avoid IndexOutOfBoundsException

            val hourMinute = parts[1].split(" ")
            if (hourMinute.size < 2) return null // Ensure AM/PM exists

            var hour = parts[0].toIntOrNull() ?: return null
            val minute = hourMinute[0].toIntOrNull() ?: return null
            val isPM = hourMinute[1].uppercase() == "PM"

            if (isPM && hour != 12) hour += 12
            if (!isPM && hour == 12) hour = 0

            return Time(hour, minute)
        } catch (e: Exception) {
            return null // Return null if parsing fails
        }
    }


    private data class Time(val hour: Int, val minute: Int)

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    fun toggleTaskCompletion(taskId: Int) {
        viewModelScope.launch {
            repository.toggleTaskCompletion(taskId)
        }
    }
}