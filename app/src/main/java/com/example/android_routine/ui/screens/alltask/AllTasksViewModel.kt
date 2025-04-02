package com.example.android_routine.ui.screens.alltask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.repository.CategoryRepository
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.data.viewmodelobject.TaskVM
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter



sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object NavigateToAddTask : UiEvent()
}



sealed class TaskEvent {
    data class ToggleComplete(val taskId: Int) : TaskEvent()
    data class Delete(val taskId: Int) : TaskEvent()
    object LoadTasks : TaskEvent()
}

class AllTasksViewModel(

    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllTasksUiState())
    val uiState: StateFlow<AllTasksUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        onEvent(TaskEvent.LoadTasks)
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.LoadTasks -> loadTasks()
            is TaskEvent.ToggleComplete -> toggleTaskCompletion(event.taskId)
            is TaskEvent.Delete -> deleteTask(event.taskId)
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val categories = categoryRepository.getCategories().first()

            taskRepository.getTasks().collectLatest { taskList ->
                val tasksVM = taskList.map { task ->
                    val categoryName = categories.find { it.id == task.categoryId }?.name ?: ""
                    TaskVM.fromEntity(task, categoryName)
                }

                _uiState.value = AllTasksUiState(
                    allTasks = tasksVM,
                    overdueTasks = tasksVM.filter { isTaskOverdue(it) }, // you'll define this helper
                    isLoading = false
                )

            }
        }
    }
    private fun toggleTaskCompletion(taskId: Int) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(taskId)?.let {
                it.copy(isCompleted = !it.isCompleted)
            }
            if (task != null) {
                taskRepository.upsert(task)
                _eventFlow.emit(UiEvent.ShowSnackbar("Task clicked"))
            }
        }
    }

    private fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(taskId)
            if (task != null) {
                taskRepository.delete(task)
                _eventFlow.emit(UiEvent.ShowSnackbar("Task deleted"))
            }
        }
    }

    private fun isTaskOverdue(task: TaskVM): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return try {
            val dueDate = LocalDate.parse(task.dueDate, formatter)
            val today = LocalDate.now()
            dueDate.isBefore(today)
        } catch (e: Exception) {
            false // return false if the date is null, badly formatted, or missing
        }
    }

}
