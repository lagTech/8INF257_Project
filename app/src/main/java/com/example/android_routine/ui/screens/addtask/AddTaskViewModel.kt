package com.example.android_routine.ui.screens.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddTaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Sealed class for one-time UI events
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class Navigate(val route: String) : UiEvent() // ← new
    }

    // Sealed class for user actions
    sealed class TaskEvent {
        data class UpdateTitle(val title: String) : TaskEvent()
        data class UpdateDueDate(val dueDate: String) : TaskEvent()
        data class UpdateDueTime(val dueTime: String) : TaskEvent()
        data class UpdatePeriodicity(val periodicity: String) : TaskEvent()
        data class UpdateNotes(val notes: String) : TaskEvent()
        data class UpdateCategory(val id: Int, val name: String) : TaskEvent()
        object Submit : TaskEvent()
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.UpdateTitle -> updateTitle(event.title)
            is TaskEvent.UpdateDueDate -> updateDueDate(event.dueDate)
            is TaskEvent.UpdateDueTime -> updateDueTime(event.dueTime)
            is TaskEvent.UpdatePeriodicity -> updatePeriodicity(event.periodicity)
            is TaskEvent.UpdateNotes -> updateNotes(event.notes)
            is TaskEvent.UpdateCategory -> updateCategory(event.id, event.name)
            is TaskEvent.Submit -> submitTask()
        }
    }

    private fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title, isError = false) }
    }

    private fun updateDueDate(dueDate: String) {
        _uiState.update { it.copy(dueDate = dueDate) }
    }

    private fun updateDueTime(dueTime: String) {
        _uiState.update { it.copy(dueTime = dueTime) }
    }

    private fun updatePeriodicity(periodicity: String) {
        _uiState.update { it.copy(periodicity = periodicity) }
    }

    private fun updateNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    private fun updateCategory(id: Int, name: String) {
        _uiState.update { it.copy(categoryId = id, categoryName = name) }
    }

    private fun submitTask() {
        val state = _uiState.value

        if (state.title.isBlank()) {
            _uiState.update { it.copy(isError = true, errorMessage = "Title cannot be empty") }
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar("Please enter a task title"))
            }
            return
        }
        if (state.categoryId == null) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar("Please select a category"))
            }
            return
        }

        if (state.dueDate.isBlank()) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar("Please select a due date"))
            }
            return
        }

        val formattedDueDate = try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = inputFormat.parse(state.dueDate)
            inputFormat.format(parsedDate ?: Date())
        } catch (e: Exception) {
            null
        }


        viewModelScope.launch {
            val newTask = Task(
                id = null,
                title = state.title,
                categoryId = state.categoryId,
                dueTime = state.dueTime.takeIf { it.isNotBlank() },
                dueDate = formattedDueDate,
                notes = state.notes.takeIf { it.isNotBlank() },
                priority = state.priority,
                periodicity = state.periodicity,
                isCompleted = false
            )

            repository.upsert(newTask)

            _eventFlow.emit(UiEvent.ShowSnackbar("Task added successfully"))
            _eventFlow.emit(UiEvent.Navigate(Screen.AllTasks.route))

        }
    }
}
