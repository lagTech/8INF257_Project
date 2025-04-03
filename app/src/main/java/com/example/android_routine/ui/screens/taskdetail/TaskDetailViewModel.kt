package com.example.android_routine.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.CategoryRepository
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.ui.screens.taskdetail.TaskDetailUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailViewModel(
    private val taskId: Int,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class Navigate(val route: String) : UiEvent()
    }

    sealed class TaskEvent {
        data class UpdateTitle(val value: String) : TaskEvent()
        data class UpdateDueDate(val value: String) : TaskEvent()
        data class UpdateDueTime(val value: String) : TaskEvent()
        data class UpdateNotes(val value: String) : TaskEvent()
        data class UpdatePeriodicity(val value: String) : TaskEvent()
        data class UpdatePriority(val value: String) : TaskEvent()
        data class UpdateCategory(val id: Int, val name: String) : TaskEvent()
        object Submit : TaskEvent()
    }

    init {
        loadTask()
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.UpdateTitle -> _uiState.update { it.copy(title = event.value, isError = false) }
            is TaskEvent.UpdateDueDate -> _uiState.update { it.copy(dueDate = event.value) }
            is TaskEvent.UpdateDueTime -> _uiState.update { it.copy(dueTime = event.value) }
            is TaskEvent.UpdateNotes -> _uiState.update { it.copy(notes = event.value) }
            is TaskEvent.UpdatePeriodicity -> _uiState.update { it.copy(periodicity = event.value) }
            is TaskEvent.UpdatePriority -> _uiState.update { it.copy(priority = event.value) }
            is TaskEvent.UpdateCategory -> _uiState.update {
                it.copy(categoryId = event.id, categoryName = event.name)
            }
            is TaskEvent.Submit -> updateTask()
        }
    }

    private fun loadTask() {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(taskId)
            val categories = categoryRepository.getCategories().first()

            task?.let {
                val categoryName = categories.find { it.id == task.categoryId }?.name ?: ""

                _uiState.value = TaskDetailUiState(
                    title = task.title,
                    categoryId = task.categoryId,
                    categoryName = categoryName,
                    dueDate = task.dueDate ?: "",
                    dueTime = task.dueTime ?: "",
                    notes = task.notes ?: "",
                    priority = task.priority ?: "",
                    periodicity = task.periodicity ?: ""
                )
            }
        }
    }

    private fun updateTask() {
        val state = _uiState.value

        if (state.title.isBlank()) {
            _uiState.update { it.copy(isError = true, errorMessage = "Title cannot be empty") }
            return
        }

        val formattedDueDate = if (state.dueDate.isNotBlank()) {
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val parsed = inputFormat.parse(state.dueDate)
                inputFormat.format(parsed ?: Date())
            } catch (e: Exception) {
                null
            }
        } else null

        viewModelScope.launch {
            val updated = Task(
                id = taskId,
                title = state.title,
                categoryId = state.categoryId,
                dueTime = state.dueTime.takeIf { it.isNotBlank() },
                dueDate = formattedDueDate,
                notes = state.notes.takeIf { it.isNotBlank() },
                priority = state.priority,
                periodicity = state.periodicity,
                isCompleted = false
            )
            taskRepository.upsert(updated)
            _eventFlow.emit(UiEvent.ShowSnackbar("Task updated successfully"))
            _eventFlow.emit(UiEvent.Navigate("allTasks"))
        }
    }
}
